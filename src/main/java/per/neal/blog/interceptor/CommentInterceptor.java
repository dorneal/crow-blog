package per.neal.blog.interceptor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.HandlerInterceptor;
import per.neal.blog.constant.Constants;
import per.neal.blog.util.ErrResponseUtil;
import per.neal.blog.util.IpUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 评论拦截器，拦截发布评论是否太快
 *
 * @author neal
 */
public class CommentInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = IpUtils.findIp(request);
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.increment(ip, 1);
        // 每个IP每小时最多发三条评论
        if (Integer.parseInt(Objects.requireNonNull(operations.get(ip))) > Constants.COMMENT_TIMES) {
            stringRedisTemplate.expire(ip, 1, TimeUnit.HOURS);
            ErrResponseUtil.render(response, "发表评论过快，请一小时后再试");
            return false;
        }
        return true;
    }
}
