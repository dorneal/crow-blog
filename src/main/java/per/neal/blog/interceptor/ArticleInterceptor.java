package per.neal.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import per.neal.blog.entity.TbVisitor;
import per.neal.blog.service.VisitorService;
import per.neal.blog.util.IpUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文章浏览监听次数
 *
 * @author neal
 */
public class ArticleInterceptor implements HandlerInterceptor {

    @Resource
    private VisitorService visitorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String articleId = request.getParameter("id");
        // 如果文章ID为空，则错误查询
        if (articleId == null || "".equals(articleId)) {
            response.sendError(400);
            return false;
        }
        // 进行浏览记录插入
        String ip = IpUtils.findIp(request);
        TbVisitor visitor = visitorService.findByIp(ip);
        if (visitor != null) {
            if (visitorService.countByIp(visitor.getId(), Long.parseLong(articleId)) == 0) {
                visitorService.newWatch(visitor.getId(), Long.parseLong(articleId));
            }
        } else {
            TbVisitor visitor1 = new TbVisitor();
            visitor1.setVisitTime(new java.sql.Timestamp(System.currentTimeMillis()));
            visitor1.setVisitTimes(1);
            visitor1.setVisitorIp(ip);
            visitor1.setVisitorLocation(IpUtils.findIpLocation(ip));
            visitorService.saveVisitor(visitor1);
        }
        return true;
    }
}
