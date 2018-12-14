package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.*;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.TbAdmin;
import per.neal.blog.entity.dto.R;
import per.neal.blog.service.BlogService;

import javax.annotation.Resource;

/**
 * 个人博客
 *
 * @author neal
 */
@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/blog")
public class BlogController {

    @ApiModelProperty("注入blog service")
    @Resource
    private BlogService blogService;

    @ApiOperation("修改博主信息")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功", response = TbAdmin.class),
            @ApiResponse(code = Constants.ERR, message = "异常信息")
    })
    @RequiresRoles(Constants.ADMIN)
    @RequiresUser
    @PutMapping("/modifyInfo")
    public R modifyInfo(@ApiParam(name = "admin", value = "TbAdmin实体类", required = true) @RequestBody TbAdmin admin) {
        try {
            blogService.updateInfo(admin);
            return R.ok(blogService.findByName(admin.getUsername()));
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }

    @ApiOperation("获取博客信息用于前台展示")
    @ApiResponses({
            @ApiResponse(code = Constants.OK,message = "站点公开信息")
    })
    @GetMapping("/blogInfo")
    public R blogInfo(){
        try {
            return R.ok(blogService.countAll());
        }catch (Exception e){
            return R.err(e.getMessage());
        }
    }

    @ApiOperation("获取博主信息用于前台展示")
    @ApiResponses({
            @ApiResponse(code = Constants.OK,message = "博主公开信息")
    })
    @GetMapping("/bloggerInfo")
    public R bloggerInfo(){
        try {
            return R.ok(blogService.find());
        }catch (Exception e){
            return R.err(e.getMessage());
        }
    }
}
