package per.neal.blog.config;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.neal.blog.constant.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置
 *
 * @author neal
 */
@Configuration
public class ShiroConfig {
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean shiro = new ShiroFilterFactoryBean();

        // 没有登录的Json返回
        shiro.setLoginUrl("/token/unLogin");
        //没有权限的json返回
        shiro.setUnauthorizedUrl("/token/noRealm");

        Map<String, String> filterChain = new LinkedHashMap<>();
        filterChain.put("/static/**", "anon");

        // swagger页面
        filterChain.put("/swagger-ui.html", "anon");
        filterChain.put("/swagger-resources", "anon");
        filterChain.put("/v2/api-docs", "anon");
        filterChain.put("/back/login.html", "anon");
        filterChain.put("/webjars/springfox-swagger-ui/**", "anon");

        // 后台管理拦截(admin角色已经在常量类中设置，如果需要更改，请同时更改)
        filterChain.put("/admin/login2.html", "anon");
        filterChain.put("/admin/assets/**", "anon");
        filterChain.put("/admin/**", "authc,roles[admin]");
        filterChain.put("/manager/**", "authc,roles[admin]");

        // token 放开(登录，未登录.....)
        filterChain.put("/token/**", "anon");

        shiro.setFilterChainDefinitionMap(filterChain);
        shiro.setSecurityManager(securityManager);
        return shiro;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(SessionDaoConfig sessionDaoConfig) {
        DefaultWebSecurityManager def = new DefaultWebSecurityManager();
        // 使用自定义的验证器
        def.setRealm(userRealm());
        SessionConfig sessionConfig = new SessionConfig();
        sessionConfig.setSessionDAO(sessionDaoConfig);
        // 注入session管理器
        def.setSessionManager(sessionConfig);
        // 注入记住我管理器
        def.setRememberMeManager(rememberMeManager());
        return def;
    }

    /**
     * 使@RequiresRoles注解生效
     *
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }


    /**
     * UserRealm
     *
     * @return UserRealm
     */
    @Bean
    public UserRealm userRealm() {
        // 使用自定义验证器
        return new UserRealm();
    }


    /**
     * cookie设置
     *
     * @return SimpleCookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie(Constants.REMEMBER_ME);
        // 记住我 cookie生效时间30天，单位秒
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * cookie 管理器
     *
     * @return CookieRememberMeManager
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // 加密秘钥，默认AES算法，秘钥长度16位
        cookieRememberMeManager.setCipherKey(Base64.decode("dsesfswfjn23409isfad2=="));
        return cookieRememberMeManager;
    }

}
