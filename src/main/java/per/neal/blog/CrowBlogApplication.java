package per.neal.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * app启动器
 *
 * @author neal
 */
@EnableTransactionManagement
@SpringBootApplication
@ServletComponentScan
@MapperScan("per.neal.blog.dao")
public class CrowBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowBlogApplication.class, args);
    }
}
