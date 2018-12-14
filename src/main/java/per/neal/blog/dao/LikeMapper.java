package per.neal.blog.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 点赞mapper
 *
 * @author neal
 */
public interface LikeMapper {
    /**
     * 统计该文章的点赞数
     *
     * @param id ID
     * @return count
     */
    int countLikeByArticleId(long id);

    /**
     * 查询该访客是否对该文章点过赞
     *
     * @param visitorId 访客ID
     * @param articleId 文章ID
     * @return count
     */
    int countLikeByVisitor(@Param("visitorId") long visitorId, @Param("articleId") long articleId);

    /**
     * 增加点赞
     *
     * @param visitorId 访客ID
     * @param articleId 文章ID
     */
    void newLike(@Param("visitorId") long visitorId, @Param("articleId") long articleId);

    /**
     * 取消点赞
     *
     * @param visitorId 访客ID
     * @param articleId 文章ID
     */
    void cancelLike(@Param("visitorId") long visitorId, @Param("articleId") long articleId);
}
