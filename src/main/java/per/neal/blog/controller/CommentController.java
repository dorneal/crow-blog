package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.TbComment;
import per.neal.blog.entity.TbVisitor;
import per.neal.blog.entity.dto.R;
import per.neal.blog.service.CommentService;
import per.neal.blog.service.VisitorService;
import per.neal.blog.util.IpUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 评论控制器
 *
 * @author neal
 */
@Api(tags = "评论控制器")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @ApiModelProperty("注入service")
    @Resource
    private CommentService commentService;
    @Resource
    private VisitorService visitorService;

    @ApiOperation("直接分页显示评论列表")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "operation success")
    })
    @GetMapping("/pagination")
    public R pagination(int page, int pageSize) {
        return R.ok(commentService.paginationComment(page, pageSize));
    }

    @ApiOperation("发布评论")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "operation success")
    })
    @PostMapping("/newComment")
    public R newComment(HttpServletRequest request, @ApiParam(name = "comment", required = true) @RequestBody TbComment comment) {
        String ip = IpUtils.findIp(request);
        TbVisitor visitor = visitorService.findByIp(ip);
        if (visitor == null) {
            visitor = newVisitor(ip);
        }
        if (visitor.getVisitorEmail() == null && visitor.getVisitorName() == null) {
            visitor.setVisitorEmail(comment.getCommentEmail());
            visitor.setVisitorName(comment.getCommentName());
            visitorService.updateInfo(visitor);
        }
        comment.setVisitorId(visitor.getId());
        commentService.saveComment(comment);
        return R.ok();
    }

    @ApiOperation("删除评论")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "operation success"),
            @ApiResponse(code = Constants.ERR, message = "operation error"),
    })
    @DeleteMapping("/deleteComment")
    public R deleteComment(long id) {
        try {
            commentService.deleteComment(id);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("根据文章ID查找评论")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "operation success", response = TbComment.class)
    })
    @GetMapping("/listByArticleId")
    public R listCommentByArticleId(long id) {
        return R.ok(commentService.listByArticleId(id));
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
