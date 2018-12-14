package per.neal.blog.service;

/**
 * 浏览service
 *
 * @author neal
 */
public interface LikeService {
    /**
     * 查询该文章的点赞量
     *
     * @param id 文章ID
     * @return COUNT
     */
    int countByArticleId(long id);
}
