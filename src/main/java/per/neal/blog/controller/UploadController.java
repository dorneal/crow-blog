package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.dto.R;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * 处理头像上传
 *
 * @author neal
 */
@Api(tags = "头像上传")
@RestController
public class UploadController {
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @ApiModelProperty("图片服务器物理地址")
    @Value("${crowBlog.imagesLocation}")
    private String imagesLocation;

    @ApiModelProperty("图片服务器映射地址")
    @Value("${crowBlog.imagesServer}")
    private String imagesServer;

    @ApiOperation("上传图片")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "图片地址", response = R.class),
            @ApiResponse(code = Constants.ERR, message = "上传出错", response = R.class)
    })
    @RequiresRoles(Constants.ADMIN)
    @PostMapping("/upload")
    public R upload(@ApiParam(name = "file", value = "MultipartFile", required = true) MultipartFile file, HttpServletRequest request) {
        String original = file.getOriginalFilename();
        assert original != null;
        File file1 = new File(imagesLocation, original);
        if (!file1.getParentFile().exists()) {
            try {
                log.info(file1.getParentFile().mkdirs() ? file1.getCanonicalPath() + "文件夹已建立" : "文件夹创建失败");
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
        try {
            file.transferTo(file1);
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            return R.ok(basePath + imagesServer + original);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return R.err("上传出错");
    }
}
