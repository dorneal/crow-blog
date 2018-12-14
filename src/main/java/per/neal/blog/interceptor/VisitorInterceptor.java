package per.neal.blog.interceptor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.HandlerInterceptor;
import per.neal.blog.entity.TbVisitor;
import per.neal.blog.service.VisitorService;
import per.neal.blog.util.IpUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 访客拦截器
 *
 * @author neal
 */
public class VisitorInterceptor implements HandlerInterceptor {

    @Resource
    private VisitorService visitorService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取IP
        String ip = IpUtils.findIp(request);

        // 获取sessionID，
        String sessionId = String.join("", request.getSession().getId().split("-")) + "byVisitor";

        // 使用redis保存sessionId
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.increment(sessionId, 1);
        // 设置1小时过期（5小时内，重复访问不会增加访问次数）
        stringRedisTemplate.expire(sessionId, 5, TimeUnit.HOURS);

        // 判断是否在统一站点访问多个页面,只做第一次访问的询问
        if (Integer.parseInt(Objects.requireNonNull(operations.get(sessionId))) == 1) {
            if (visitorService.countVisitorByIp(ip) < 1) {
                // 第一次来访
                TbVisitor visitor = new TbVisitor();
                String location = IpUtils.findIpLocation(ip);
                visitor.setVisitorIp(ip);
                visitor.setVisitTime(new java.sql.Timestamp(System.currentTimeMillis()));
                visitor.setVisitTimes(1);
                visitor.setVisitorLocation(location);

                visitorService.saveVisitor(visitor);
            } else {
                // 来访次数增加
                TbVisitor visitor = visitorService.findByIp(ip);
                Date date = new Date(visitor.getVisitTime().getTime());
                // 判断时间是否还是今天，是则不改变
                if (dateEquals(date)) {
                    visitorService.updateVisitTimes(visitor.getId());
                }
            }
        }
        return true;
    }

    /**
     * 日期比较
     *
     * @param date date
     * @return 今天是否在date之后
     */
    private boolean dateEquals(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate ago = LocalDateTime.ofInstant(instant, zoneId).toLocalDate();
        LocalDate today = LocalDate.now();
        return today.isAfter(ago);
    }
}
