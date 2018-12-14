package per.neal.blog.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.neal.blog.dao.BlogMapper;
import per.neal.blog.dao.SiteCountMapper;
import per.neal.blog.entity.TbAdmin;
import per.neal.blog.entity.TbSite;
import per.neal.blog.service.BlogService;

import javax.annotation.Resource;

/**
 * 博客service实现类
 *
 * @author neal
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogMapper blogMapper;
    @Resource
    private SiteCountMapper siteCountMapper;

    @Override
    public TbAdmin findByName(String username) {
        return blogMapper.findByName(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(TbAdmin admin) {
        blogMapper.updateInfo(admin);
    }

    @Override
    @Cacheable(cacheNames = "blogger", key = "'bloggerInfo'")
    public TbAdmin find() {
        return blogMapper.find();
    }

    @Override
    public long articleCount() {
        return siteCountMapper.articleCount();
    }

    @Override
    public long commentCount() {
        return siteCountMapper.commentCount();
    }

    @Override
    public long visitorCount() {
        return siteCountMapper.visitorCount();
    }

    @Override
    public long columnCount() {
        return siteCountMapper.columnCount();
    }

    @Override
    public long likeCount() {
        return siteCountMapper.likeCount();
    }

    @Override
    public TbSite countAll() {
        // 查询出所有站点count,然后进行更新
        TbSite site = new TbSite();
        site.setArticleCount(siteCountMapper.articleCount());
        site.setCommentCount(siteCountMapper.commentCount());
        site.setLikeCount(siteCountMapper.likeCount());
        site.setTagsCount(siteCountMapper.columnCount());
        site.setVisitCount(siteCountMapper.visitorCount());
        siteCountMapper.updateCount(site);
        return siteCountMapper.countAll();
    }

    @Override
    public void updateCount(TbSite site) {
        siteCountMapper.updateCount(site);
    }
}
