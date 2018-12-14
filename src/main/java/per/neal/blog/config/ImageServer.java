package per.neal.blog.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

/**
 * 配置图片服务器(最终访问地址 /images/xxx.jpg)
 *
 * @author neal
 */
@Configuration
public class ImageServer implements WebMvcConfigurer {
    /**
     * 图片映射地址
     */
    @Value("${crowBlog.imagesPath}")
    private String imagesPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if ("".equals(imagesPath) || "${crowBlog.imagesPath}".equals(imagesPath)) {
            String imagesPath = Objects.requireNonNull(ImageServer.class.getClassLoader().getResource("")).getPath();
            if (imagesPath.indexOf(".jar") > 0) {
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
            } else if (imagesPath.indexOf("classes") > 0) {
                imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
            }
            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
            this.imagesPath = imagesPath;
        }
        LoggerFactory.getLogger(ImageServer.class).info("imagesPath=" + imagesPath);
        registry.addResourceHandler("/images/**").addResourceLocations(imagesPath);
    }
}
