package per.neal.blog.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.SolrCrudRepository;
import per.neal.blog.entity.ArticleModel;

/**
 * 文章 repository
 *
 * @author neal
 */
public interface ArticleRepository extends SolrCrudRepository<ArticleModel, String> {


    /**
     * 高亮查询
     *
     * @param pageable 分页对象
     * @return HighlightPage
     */
    @Highlight(prefix = "<i style='color:red;'>", postfix = "</i>")
    HighlightPage<ArticleModel> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageable);
}
