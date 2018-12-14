package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.dto.R;
import per.neal.blog.service.LikeService;

import javax.annotation.Resource;

/**
 * 浏览service
 *
 * @author neal
 */
@Api(tags = "点赞控制器")
@RestController
@RequestMapping("/like")
public class LikeController {
    @ApiModelProperty("点赞service")
    @Resource
    private LikeService likeService;

    @ApiOperation("根据文章ID查询该文章的点赞数")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "点赞数")
    })
    @GetMapping("/likes")
    public R watches(long id) {
        return R.ok(likeService.countByArticleId(id));
    }
}
