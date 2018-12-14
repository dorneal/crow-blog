package per.neal.blog.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.TbAdmin;
import per.neal.blog.service.BlogService;

import javax.annotation.Resource;

/**
 * Realm配置
 *
 * @author neal
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private BlogService blogService;

    @Override
    public String getName() {
        return "userRealm";
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        TbAdmin admin = blogService.findByName(username);
        if (admin != null) {
            simpleAuthorizationInfo.addRole(Constants.ADMIN);
        } else {
            simpleAuthorizationInfo.addRole(Constants.GUEST);
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        TbAdmin admin = blogService.findByName(username);
        if (null == admin) {
            throw new AccountException("帐号或密码不正确！");
        }
        return new SimpleAuthenticationInfo(username, admin.getPassKey(), getName());
    }

}
