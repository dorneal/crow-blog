package per.neal.blog.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 浏览mapper
 *
 * @author neal
 */
public interface WatchMapper {
    /**
     * 查询该文章的浏览量
     *
     * @param id 文章ID
     * @return COUNT
     */
    int countByArticleId(long id);

    /**
     * 查询该访客是否浏览过该文章
     *
     * @param visitorId 访客ID
     * @param articleId 文章ID
     * @return count
     */
    int countByIp(@Param("visitorId") long visitorId, @Param("articleId") long articleId);

    /**
     * 插入浏览记录
     *
     * @param visitorId 访问者ID
     * @param articleId 文章ID
     */
    void newWatch(@Param("visitorId") long visitorId, @Param("articleId") long articleId);

}
