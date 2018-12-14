package per.neal.blog.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS 过滤器
 *
 * @author neal
 */
@WebFilter(filterName = "myXssSpringFilter", urlPatterns = {"/search/searchArticle", "/comment/newComment"}, asyncSupported = true)
public class MyXssSpringFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
    }
}