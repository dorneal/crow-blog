package per.neal.blog.service.impl;

import org.springframework.stereotype.Service;
import per.neal.blog.dao.LikeMapper;
import per.neal.blog.service.LikeService;

import javax.annotation.Resource;

/**
 * 点赞service
 *
 * @author neal
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    private LikeMapper likeMapper;

    @Override
    public int countByArticleId(long id) {
        return likeMapper.countLikeByArticleId(id);
    }
}
