package per.neal.blog.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.neal.blog.entity.EmailInfo;
import per.neal.blog.entity.dto.R;
import per.neal.blog.util.SendEmail;

import javax.annotation.Resource;

/**
 * 邮箱发送控制器
 *
 * @author neal
 */
@Api(tags = "邮箱发送控制器")
@RestController
@RequestMapping("/email")
public class EmailController {

    @ApiModelProperty("注入工具类")
    @Resource
    private SendEmail sendEmail;

    @ApiOperation("发送邮件")
    @PostMapping("/sendEmail")
    public R sendEmail(@ApiParam(name = "emailInfo", required = true) @RequestBody EmailInfo emailInfo) {
        try {
            sendEmail.sendHtmlEmail(emailInfo);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.err(e.getLocalizedMessage());
        }
    }
}
