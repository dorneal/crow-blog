package per.neal.blog.controller;

import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.dto.R;
import per.neal.blog.service.ArticleSearchService;

import javax.annotation.Resource;

/**
 * 搜索控制器
 *
 * @author neal
 */
@Api(tags = "搜索控制器")
@RestController
@RequestMapping("/search")
public class ArticleSearchController {

    @ApiModelProperty("solr搜索service")
    @Resource
    private ArticleSearchService articleSearchService;

    @ApiOperation("文章搜索")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "solr分页对象")
    })
    @GetMapping("/searchArticle")
    public R searchArticle(@RequestParam("q") String query, @PageableDefault(size = ArticleSearchService.DEFAULT_PAGE_SIZE) Pageable pageable) {
        return R.ok(articleSearchService.findByFields(query, pageable));
    }

    @ApiOperation("搜索索引重建")
    @ApiResponses({
            @ApiResponse(code = Constants.OK, message = "操作成功")
    })
    @GetMapping("/reIndex")
    public R reIndex() {
        articleSearchService.reIndex();
        return R.ok();
    }
}
