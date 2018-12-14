package per.neal.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.neal.blog.dao.CommentMapper;
import per.neal.blog.entity.TbComment;
import per.neal.blog.entity.dto.PageResult;
import per.neal.blog.service.CommentService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论service实现类
 *
 * @author neal
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public int countByArticleId(long id) {
        return commentMapper.countByArticleId(id);
    }

    @Override
    public List<TbComment> listByArticleId(long id) {
        return commentMapper.listByArticleId(id);
    }

    @Override
    public PageResult paginationComment(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        Page<TbComment> page1 = (Page<TbComment>) commentMapper.listComment();
        return new PageResult(page1.getTotal(), page1.getResult());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveComment(TbComment comment) {
        comment.setCommentTime(new java.sql.Timestamp(System.currentTimeMillis()));
        comment.setHigherId(0);
        commentMapper.saveComment(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(long id) {
        commentMapper.deleteComment(id);
    }
}
