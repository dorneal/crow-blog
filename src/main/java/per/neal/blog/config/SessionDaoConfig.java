package per.neal.blog.config;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 重写session dao，使用redis管理
 *
 * @author neal
 */
@Component
public class SessionDaoConfig extends EnterpriseCacheSessionDAO {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable serializable = this.generateSessionId(session);
        SimpleSession simpleSession = (SimpleSession) session;
        simpleSession.setId(serializable);
        return simpleSession;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return (Session) redisTemplate.opsForValue().get(sessionId);
    }

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession) {
            if (((ValidatingSession) session).isValid()) {
                redisTemplate.opsForValue().set(session.getId(), session);
            } else {
                redisTemplate.delete(session.getId());
            }
        } else {
            redisTemplate.opsForValue().set(session.getId(), session);
        }
    }

    @Override
    protected void doDelete(Session session) {
        redisTemplate.delete(session.getId());
    }
}
