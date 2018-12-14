package per.neal.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import per.neal.blog.entity.ArticleModel;


/**
 * 文章搜索接口
 *
 * @author neal
 */
public interface ArticleSearchService {
    /**
     * 页面大小
     */
    int DEFAULT_PAGE_SIZE = 6;

    /**
     * 根据关键字查询
     *
     * @param fields   关键字组合
     * @param pageable 分页对象
     * @return list
     */
    Page<ArticleModel> findByFields(String fields, Pageable pageable);

    /**
     * 重建搜索索引
     */
    void reIndex();
}
