package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.dto.R;
import per.neal.blog.service.WatchService;

import javax.annotation.Resource;

/**
 * 浏览service
 *
 * @author neal
 */
@Api(tags = "浏览控制器")
@RestController
@RequestMapping("/watch")
public class WatchController {
    @ApiModelProperty("浏览service")
    @Resource
    private WatchService watchService;

    @ApiOperation("根据文章ID查询该文章的浏览数")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "浏览数")
    })
    @GetMapping("/watches")
    public R watches(long id) {
        return R.ok(watchService.countByArticleId(id));
    }
}
