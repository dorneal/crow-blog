package per.neal.blog.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import per.neal.blog.interceptor.ArticleInterceptor;
import per.neal.blog.interceptor.CommentInterceptor;
import per.neal.blog.interceptor.IdentityInterceptor;
import per.neal.blog.interceptor.VisitorInterceptor;
import per.neal.blog.util.SendEmail;


/**
 * WEB 配置
 *
 * @author neal
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 登录次数拦截
     *
     * @return IdentityInterceptor
     */
    @Bean
    public IdentityInterceptor identityInterceptor() {
        return new IdentityInterceptor();
    }

    /**
     * 来访者拦截器
     *
     * @return VisitorInterceptor
     */
    @Bean
    public VisitorInterceptor visitorInterceptor() {
        return new VisitorInterceptor();
    }

    /**
     * 查询文章进行拦截，访问记录添加
     *
     * @return ArticleInterceptor
     */
    @Bean
    public ArticleInterceptor articleInterceptor() {
        return new ArticleInterceptor();
    }

    @Bean
    public CommentInterceptor commentInterceptor() {
        return new CommentInterceptor();
    }

    /**
     * 配置拦截器
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitorInterceptor()).addPathPatterns("/client/**", "/index.html").order(1);
        registry.addInterceptor(identityInterceptor()).addPathPatterns("/token/login");
        registry.addInterceptor(articleInterceptor()).addPathPatterns("/article/article");
        registry.addInterceptor(commentInterceptor()).addPathPatterns("/comment/newComment");
    }

    /**
     * 邮件发送工具类
     *
     * @return SendEmail
     */
    @Bean(name = "sendEmail")
    public SendEmail sendEmail() {
        return new SendEmail();
    }

    /**
     * 自定义内嵌tomcat
     *
     * @return ConfigurableServletWebServerFactory
     */
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(8080);
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notfound.html"));
        return factory;
    }
}
