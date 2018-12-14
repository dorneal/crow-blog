package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.TbVisitor;
import per.neal.blog.entity.dto.R;
import per.neal.blog.service.VisitorService;
import per.neal.blog.util.IpUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 来访者控制器
 *
 * @author neal
 */
@Api(tags = "来访者控制器")
@RestController
@RequestMapping("/visitor")
public class VisitorController {
    @ApiModelProperty("注入service")
    @Resource
    private VisitorService visitorService;

    @ApiOperation("搜索或直接分页显示来访者列表")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "operation success")
    })
    @PostMapping("/search")
    public R search(int page, int pageSize, @ApiParam(name = "visitor") @RequestBody TbVisitor visitor) {
        return R.ok(visitorService.paginationVisitor(page, pageSize, visitor));
    }

    @ApiOperation("从请求获取来访者信息")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "operation success", response = TbVisitor.class)
    })
    @GetMapping("/visitorInfo")
    public R visitorInfo(HttpServletRequest request) {
        String ip = IpUtils.findIp(request);
        return R.ok(visitorService.findByIp(ip));
    }

    @ApiOperation("根据邮箱查找来访者")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "operation success", response = TbVisitor[].class),
            @ApiResponse(code = Constants.ERR, message = "not found")
    })
    @GetMapping("/findByEmail")
    public R findByEmail(@ApiParam(name = "email", required = true) String email) {
        List<TbVisitor> list = visitorService.listByEmail(email);
        if (list.size() == 0) {
            return R.err("未找到该邮箱用户");
        }
        return R.ok(list.get(0));
    }
}
