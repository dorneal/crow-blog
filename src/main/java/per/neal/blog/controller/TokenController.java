package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import per.neal.blog.constant.Constants;
import per.neal.blog.entity.TbAdmin;
import per.neal.blog.entity.Token;
import per.neal.blog.entity.dto.R;
import per.neal.blog.service.BlogService;
import per.neal.blog.util.IpUtils;
import per.neal.blog.util.Md5Utils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;


/**
 * 基础控制器，处理登录，未登录
 *
 * @author neal
 */
@Api(tags = "基础控制器")
@RestController
@RequestMapping("/token")
public class TokenController {
    private static final Logger log = LoggerFactory.getLogger(TokenController.class);

    @Resource
    private BlogService blogService;

    @ApiOperation("处理未登录")
    @ApiResponses({
            @ApiResponse(code = Constants.ERR, message = "未登录", response = R.class)
    })
    @GetMapping("/unLogin")
    public R unLogin() {
        return R.err("未登录");
    }

    @ApiOperation("处理没权限")
    @ApiResponses({
            @ApiResponse(code = Constants.ERR, message = "没有权限", response = R.class)
    })
    @GetMapping("/noRealm")
    public R noRealm() {
        return R.err("没有权限");
    }

    @ApiOperation("登录操作")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "登录成功，返回对象", response = Token.class),
            @ApiResponse(code = Constants.ERR, message = "登录失败，返回错误信息")
    })
    @PostMapping("/login")
    public R login(@ApiParam(name = "admin", value = "Token实体类", required = true) @RequestBody Token admin, HttpServletRequest request) {
        String username = admin.getUsername();
        // 加密
        String passKey = Md5Utils.encryption(admin.getPassKey());
        UsernamePasswordToken token = new UsernamePasswordToken(username, passKey, admin.isRememberMe());
        Subject subject = SecurityUtils.getSubject();
        String msg;
        try {
            subject.login(token);
            Session session = subject.getSession();
            session.setAttribute(Constants.CURRENT_USER, username);
            TbAdmin user = blogService.findByName(username);

            // 更新最后登录时间跟IP
            user.setLastIp(IpUtils.findIp(request));
            user.setLastTime(new Timestamp(System.currentTimeMillis()));
            blogService.updateInfo(user);

            // FIXME 密码 transient 不参与序列化无效
            user.setPassKey(null);
            return R.ok(user, Constants.OK, "index.html");
        } catch (IncorrectCredentialsException e) {
            msg = "登录密码错误. Password for account " + token.getPrincipal() + " was incorrect.";
            log.error(e.getMessage());
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败次数过多";
            log.error(e.getMessage());
        } catch (LockedAccountException e) {
            msg = "帐号已被锁定. The account for username " + token.getPrincipal() + " was locked.";
            log.error(e.getMessage());
        } catch (DisabledAccountException e) {
            msg = "帐号已被禁用. The account for username " + token.getPrincipal() + " was disabled.";
            log.error(e.getMessage());
        } catch (ExpiredCredentialsException e) {
            msg = "帐号已过期. the account for username " + token.getPrincipal() + "  was expired.";
            log.error(e.getMessage());
        } catch (NullPointerException | AuthenticationException e) {
            msg = "帐号不存在. There is no user with username of " + token.getPrincipal();
            log.error(e.getMessage());
        } catch (UnauthorizedException e) {
            msg = "您没有得到相应的授权！" + e.getMessage();
            log.error(e.getMessage());
        }
        return R.err(msg);
    }


    @ApiOperation("退出登录")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "退出成功", response = R.class)
    })
    @RequiresUser
    @DeleteMapping("logout")
    public R logout() {
        try {
            SecurityUtils.getSubject().logout();
            return R.ok();
        } catch (Exception e) {
            log.info(e.getMessage());
            return R.err(e.getLocalizedMessage());
        }
    }
}
