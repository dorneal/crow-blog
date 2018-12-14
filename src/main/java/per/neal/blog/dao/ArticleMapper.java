package per.neal.blog.dao;

import org.apache.ibatis.annotations.Param;
import per.neal.blog.entity.ArticleRankModel;
import per.neal.blog.entity.TbArticle;

import java.util.List;

/**
 * 文章mapper
 *
 * @author neal
 */
public interface ArticleMapper {
    /**
     * 查询所有文章,包括停用，定时的
     *
     * @return list
     */
    List<TbArticle> listArticle();

    /**
     * 统计文章数，包括停用，定时的
     *
     * @return count
     */
    int countArticle();

    /**
     * 标题关键字统计文章
     *
     * @param articleTitle 标题关键字
     * @return count
     */
    int countArticleByKeywords(@Param("articleTitle") String articleTitle);

    /**
     * 根据ID 查询文章
     *
     * @param id ID
     * @return 文章
     */
    TbArticle findArticleById(long id);


    /**
     * 更新文章
     *
     * @param article article
     */
    void updateArticle(TbArticle article);

    /**
     * 插入新文章
     *
     * @param article 文章
     */
    void newArticle(TbArticle article);

    /**
     * 删除文章(软删除)
     *
     * @param id id
     */
    void removeArticle(long id);

    /**
     * 启用文章
     *
     * @param id ID
     */
    void enableArticle(long id);

    /**
     * 检查标题是否重复
     *
     * @param title 文章标题
     * @return count
     */
    int countByTitle(String title);

    /**
     * 根据栏目ID，查询所属的文章
     *
     * @param columnId 栏目ID
     * @return list
     */
    List<TbArticle> listByColumnId(long columnId);

    /**
     * 统计该栏目下的文章数
     *
     * @param columnId 栏目ID
     * @return COUNT
     */
    int countByColumnId(long columnId);


    /**
     * 分页查询
     *
     * @param offset   偏移量(当前页码-1)
     * @param pageSize 页面大小
     * @return list
     */
    List<TbArticle> pagination(@Param("offset") int offset, @Param("pageSize") int pageSize);

    /**
     * 分页模糊查询
     *
     * @param offset       偏移量(当前页码-1)
     * @param pageSize     页面大小
     * @param articleTitle 文章标题
     * @return list
     */
    List<TbArticle> paginationByCondition(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("articleTitle") String articleTitle);

    /**
     * 根据浏览量查询前三文章
     *
     * @return list
     */
    List<ArticleRankModel> rankByVisit();

    /**
     * 根据点赞量查询前三文章
     *
     * @return list
     */
    List<ArticleRankModel> rankByLike();

    /**
     * 查询最新的前6片文章
     *
     * @return list
     */
    List<TbArticle> newestArticle();
}