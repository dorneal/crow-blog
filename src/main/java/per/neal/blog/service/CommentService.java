package per.neal.blog.service;

import per.neal.blog.entity.TbComment;
import per.neal.blog.entity.dto.PageResult;

import java.util.List;

/**
 * 评论service接口
 *
 * @author neal
 */
public interface CommentService {
    /**
     * 统计该ID下的评论条数
     *
     * @param id 文章ID
     * @return count
     */
    int countByArticleId(long id);

    /**
     * 根据文章id统计文章下的评论
     *
     * @param id 文章ID
     * @return list
     */
    List<TbComment> listByArticleId(long id);

    /**
     * 根据地址搜索分页查询
     *
     * @param page     当前页
     * @param pageSize 页面大小
     * @return PageResult
     */
    PageResult paginationComment(int page, int pageSize);

    /**
     * 保存评论
     *
     * @param comment TbComment
     */
    void saveComment(TbComment comment);

    /**
     * 删除评论
     *
     * @param id 评论ID
     */
    void deleteComment(long id);
}
