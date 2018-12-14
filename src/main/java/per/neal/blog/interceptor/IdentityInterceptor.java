package per.neal.blog.interceptor;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.HandlerInterceptor;
import per.neal.blog.constant.Constants;
import per.neal.blog.util.ErrResponseUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 根据sessionID ，登录失败重试拦截
 *
 * @author neal
 */
public class IdentityInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // sessionId
        String id = String.join("", request.getSession().getId().split("-")) + "byAdmin";
        if (request.getAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID) != null) {
            // 如果是已经登录在重试登录，直接跳过
            return true;
        }
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.increment(id, 1);
        if (Integer.parseInt(Objects.requireNonNull(operations.get(id))) > Constants.MAX_RETRY_COUNT) {
            // 当登录重试次数超过5次
            stringRedisTemplate.expire(id, 1, TimeUnit.HOURS);
            String errMsg = "失败次数超过5次，请一小时后再试！！！";
            ErrResponseUtil.render(response, errMsg);
            return false;
        }
        return true;
    }
}
