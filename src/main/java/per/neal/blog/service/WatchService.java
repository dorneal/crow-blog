package per.neal.blog.service;

/**
 * 浏览service
 *
 * @author neal
 */
public interface WatchService {
    /**
     * 查询该文章的浏览量
     *
     * @param id 文章ID
     * @return COUNT
     */
    int countByArticleId(long id);
}
