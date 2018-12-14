package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.*;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.TbArticle;
import per.neal.blog.entity.TbTiming;
import per.neal.blog.entity.TbVisitor;
import per.neal.blog.entity.dto.ArticleTiming;
import per.neal.blog.entity.dto.ArticleVo;
import per.neal.blog.entity.dto.R;
import per.neal.blog.service.ArticleService;
import per.neal.blog.service.QuartzService;
import per.neal.blog.service.VisitorService;
import per.neal.blog.util.IpUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 文章控制器
 *
 * @author neal
 */
@Api(tags = "文章控制器")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @ApiModelProperty("注入文章service")
    @Resource
    private ArticleService articleService;

    @ApiModelProperty("注入定时器service")
    @Resource
    private QuartzService quartzService;

    @ApiModelProperty("注入访问者service")
    @Resource
    private VisitorService visitorService;

    @ApiOperation("发布文章")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功"),
            @ApiResponse(code = Constants.ERR, message = "操作失败")
    })
    @RequiresRoles(Constants.ADMIN)
    @PostMapping("/newArticle")
    public R newArticle(@ApiParam(name = "article", value = "TbArticle实体类", required = true) @RequestBody ArticleTiming article) {
        TbArticle thisArticle = article.getArticle();
        if (articleService.countByTitle(thisArticle.getTitle().trim()) > 0) {
            return R.err("标题已存在，请另起标题");
        }
        try {
            articleService.newArticle(thisArticle);
            if (thisArticle.getArticleStatus() == Constants.JOB_WORK_WAIT_STATUS) {
                TbTiming tbTiming = article.getTiming();
                tbTiming.setArticleId(thisArticle.getId());
                tbTiming.setWorkStatus("1");
                articleService.saveTiming(tbTiming);
                // 添加任务
                quartzService.addJob(tbTiming.getWorkTime().getTime(), thisArticle.getId());
            }
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("更新文章")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功"),
            @ApiResponse(code = Constants.ERR, message = "操作失败异常信息")
    })
    @RequiresRoles(Constants.ADMIN)
    @PutMapping("/modifyArticle")
    public R modifyArticle(@ApiParam(name = "article", value = "TbArticle实体类", required = true) @RequestBody ArticleTiming article) {
        // 修改时，需要注意，修改的名字是否跟以前的名字一样，一样则跳过栏目名检查。
        TbArticle thisArticle = article.getArticle();
        String title = articleService.findArticleById(thisArticle.getId()).getTitle();
        if (!title.equalsIgnoreCase(thisArticle.getTitle())) {
            if (articleService.countByTitle(thisArticle.getTitle()) > 0) {
                return R.err("标题已存在，请另起标题");
            }
        }
        try {
            if (thisArticle.getArticleStatus() == Constants.JOB_WORK_WAIT_STATUS) {
                // 定时发布状态，则更新定时发布表
                articleService.updateTiming(article.getTiming());
                // 修改任务执行时间)
                if (!quartzService.updateJobWorkTime(article.getTiming().getWorkTime().getTime(), thisArticle.getId())) {
                    return R.err();
                }
            }
            articleService.updateArticle(thisArticle);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("移除文章（软删除）")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功"),
            @ApiResponse(code = Constants.ERR, message = "操作失败异常信息")
    })
    @RequiresRoles(Constants.ADMIN)
    @DeleteMapping("/removeArticle")
    public R removeArticle(long[] ids) {
        try {
            articleService.batchRemove(ids);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("启用文章")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功"),
            @ApiResponse(code = Constants.ERR, message = "操作失败异常信息")
    })
    @PutMapping("/enableArticle")
    public R enableArticle(@RequestBody long id) {
        try {
            articleService.enableArticle(id);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("查找文章")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功", response = TbArticle.class),
            @ApiResponse(code = Constants.ERR, message = "操作失败异常信息")
    })
    @GetMapping("/findArticle")
    public R findArticle(long id) {
        ArticleTiming articleTiming = new ArticleTiming();
        try {
            articleTiming.setArticle(articleService.findArticleById(id));
            articleTiming.setTiming(articleService.findByArticleId(id));
            return R.ok(articleTiming);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("查询所有文章")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功", response = TbArticle.class),
            @ApiResponse(code = Constants.ERR, message = "操作失败异常信息")
    })
    @PostMapping("/listArticle")
    public R listArticle(int page, int pageSize, @ApiParam(name = "article", value = "TbArticle实体类", required = true) @RequestBody TbArticle article) {
        try {
            return R.ok(articleService.articles(page, pageSize, article));
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("查询该ID栏目下的所有文章")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功", response = TbArticle.class),
            @ApiResponse(code = Constants.ERR, message = "操作失败异常信息")
    })
    @GetMapping("/articlesByColumnId")
    public R articlesByColumnId(long id) {
        try {
            return R.ok(articleService.listByColumnId(id));
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("定时发布文章")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功"),
            @ApiResponse(code = Constants.ERR, message = "操作失败异常信息")
    })
    @PostMapping("/timingPublish")
    public R timingPublish(long timing, @ApiParam(name = "article", value = "TbArticle实体类", required = true) @RequestBody TbArticle article) {
        if (timing <= System.currentTimeMillis()) {
            return R.err("所选时间必须大于现在");
        }
        try {
            articleService.newArticle(article);
            quartzService.addJob(timing, article.getId());
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("文章浏览排行榜")
    @GetMapping("/visitRank")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "文章浏览数排行榜")
    })
    public R visitRank() {
        return R.ok(articleService.rankByVisit());
    }

    @ApiOperation("文章点赞排行榜")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "文章点赞数排行榜")
    })
    @GetMapping("/likeRank")
    public R likeRank() {
        return R.ok(articleService.rankByLike());
    }

    @ApiOperation("查询文章用于前端显示")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "文章包装类", response = ArticleVo.class)
    })
    @GetMapping("/article")
    public R article(long id) {
        return R.ok(articleService.article(id));
    }

    @ApiOperation("查询该访客是否对该文章点过赞")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "是否点赞")
    })
    @GetMapping("/whetherLike")
    public R whetherLike(long articleId, HttpServletRequest request) {
        String ip = IpUtils.findIp(request);
        TbVisitor visitor = visitorService.findByIp(ip);
        if (visitor == null) {
            newVisitor(ip);
            return R.ok(0);
        }
        return R.ok(articleService.countLikeByVisitor(visitor.getId(), articleId));
    }

    @ApiOperation("点赞")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功")
    })
    @GetMapping("/like")
    public R like(long articleId, HttpServletRequest request) {
        String ip = IpUtils.findIp(request);
        TbVisitor visitor = visitorService.findByIp(ip);
        if (visitor == null) {
            visitor = newVisitor(ip);
        }
        int count = articleService.countLikeByVisitor(visitor.getId(), articleId);
        if (count > 0) {
            // 取消点赞
            articleService.cancelLike(visitor.getId(), articleId);
            return R.ok(0);
        }
        try {
            articleService.saveLike(visitor.getId(), articleId);
            return R.ok(1);
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("最新文章")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功")
    })
    @GetMapping("/newestArticle")
    public R newestArticle() {
        return R.ok(articleService.newestArticle());
    }

    /**
     * 保存访客记录
     *
     * @param ip IP
     * @return TbVisitor
     */
    private TbVisitor newVisitor(String ip) {
        return IpUtils.getTbVisitor(ip, visitorService);
    }
}
