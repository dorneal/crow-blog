package per.neal.blog.util;

import com.google.gson.Gson;
import per.neal.blog.entity.dto.R;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 封装响应信息
 *
 * @author neal
 */
public final class ErrResponseUtil {

    private ErrResponseUtil() {
        throw new AssertionError("create error");
    }

    /**
     * 将信息返回前台
     *
     * @param response HttpServletResponse
     * @param errMsg   错误信息
     * @throws Exception Exception
     */
    public static void render(HttpServletResponse response, String errMsg) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        Gson gson = new Gson();
        String str = gson.toJson(R.err(errMsg), R.class);
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
