package per.neal.blog.util;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import per.neal.blog.entity.IpModel;
import per.neal.blog.entity.TbVisitor;
import per.neal.blog.service.VisitorService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * IP获取工具类
 *
 * @author neal
 */
public final class IpUtils {
    private IpUtils() {
        throw new AssertionError("create error");
    }

    /**
     * ip查询API接口
     */
    private static final String FIND_API = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    /**
     * 获取请求者IP地址
     *
     * @param request HttpServletRequest
     * @return IP
     */
    public static String findIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                assert inet != null;
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','
        // 分割 "***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 根据IP地址查询IP所在位置
     *
     * @param ip IP
     * @return 地址
     */
    public static String findIpLocation(String ip) throws IOException {
        URL url = new URL(FIND_API + ip);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        StringBuilder json = new StringBuilder();
        while (scanner.hasNext()) {
            json.append(scanner.next());
        }

        JsonObject object = new JsonParser().parse(json.toString()).getAsJsonObject();
        IpModel model = new Gson().fromJson(object.get("data").getAsJsonObject().toString(), IpModel.class);
        return model.toString();
    }

    /**
     * 填充访客信息
     *
     * @param ip             IP
     * @param visitorService service接口
     * @return TbVisitor
     */
    public static TbVisitor getTbVisitor(String ip, VisitorService visitorService) {
        TbVisitor visitor;
        visitor = new TbVisitor();
        visitor.setVisitorIp(ip);
        try {
            visitor.setVisitorLocation(IpUtils.findIpLocation(ip));
        } catch (IOException e) {
            visitor.setVisitorLocation("XXX内网IP");
        }
        visitor.setVisitTimes(1);
        visitor.setVisitTime(new java.sql.Timestamp(System.currentTimeMillis()));
        visitorService.saveVisitor(visitor);
        return visitor;
    }
}
