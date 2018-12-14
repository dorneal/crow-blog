package per.neal.blog.service;

import per.neal.blog.entity.ArticleRankModel;
import per.neal.blog.entity.TbArticle;
import per.neal.blog.entity.TbTiming;
import per.neal.blog.entity.dto.ArticleVo;
import per.neal.blog.entity.dto.PageResult;

import java.util.List;

/**
 * 文章service接口
 *
 * @author neal
 */
public interface ArticleService {

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
     * @param ids id数组
     */
    void batchRemove(long[] ids);

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
     * 后台分页
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @param article  文章
     * @return PageResult
     */
    PageResult articles(int page, int pageSize, TbArticle article);

    /**
     * 更新发布时间
     *
     * @param tbTiming TbTiming
     */
    void updateTiming(TbTiming tbTiming);

    /**
     * 新增定时发布数据
     *
     * @param tbTiming TbTiming
     */
    void saveTiming(TbTiming tbTiming);

    /**
     * 根据文章ID查找该任务
     *
     * @param id 文章ID
     * @return TbTiming
     */
    TbTiming findByArticleId(long id);


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
     * 根据ID查询该文章包装类
     *
     * @param id ID
     * @return ArticleVo
     */
    ArticleVo article(long id);

    /**
     * 统计该访客是否对该文章点过赞
     *
     * @param visitorId 访客ID
     * @param articleId 文章ID
     * @return int
     */
    int countLikeByVisitor(long visitorId, long articleId);

    /**
     * 点赞
     *
     * @param visitorId 访客ID
     * @param articleId 文章ID
     */
    void saveLike(long visitorId, long articleId);

    /**
     * 取消点赞
     *
     * @param visitorId 访客ID
     * @param articleId 文章ID
     */
    void cancelLike(long visitorId, long articleId);

    /**
     * 查询最新的前6片文章
     *
     * @return list
     */
    List<TbArticle> newestArticle();
}
