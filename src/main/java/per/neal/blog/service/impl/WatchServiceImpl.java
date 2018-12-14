package per.neal.blog.service.impl;

import org.springframework.stereotype.Service;
import per.neal.blog.dao.WatchMapper;
import per.neal.blog.service.LikeService;
import per.neal.blog.service.WatchService;

import javax.annotation.Resource;

/**
 * 浏览service
 *
 * @author neal
 */
@Service
public class WatchServiceImpl implements WatchService {
    @Resource
    private WatchMapper watchMapper;

    @Override
    public int countByArticleId(long id) {
        return watchMapper.countByArticleId(id);
    }
}
