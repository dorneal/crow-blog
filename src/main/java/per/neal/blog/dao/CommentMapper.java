package per.neal.blog.dao;


import per.neal.blog.entity.TbComment;

import java.util.List;

/**
 * 评论mapper
 *
 * @author neal
 */
public interface CommentMapper {

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
     * 查询所有的评论用于后台展示
     *
     * @return list
     */
    List<TbComment> listComment();

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
