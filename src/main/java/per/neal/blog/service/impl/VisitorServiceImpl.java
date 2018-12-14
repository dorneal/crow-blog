package per.neal.blog.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import per.neal.blog.dao.VisitorMapper;
import per.neal.blog.dao.WatchMapper;
import per.neal.blog.entity.TbVisitor;
import per.neal.blog.entity.dto.PageResult;
import per.neal.blog.service.VisitorService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 来访者service实现类
 *
 * @author neal
 */
@Service
public class VisitorServiceImpl implements VisitorService {
    @Resource
    private VisitorMapper visitorMapper;
    @Resource
    private WatchMapper watchMapper;

    @Override
    public List<TbVisitor> listVisitor() {
        return visitorMapper.listVisitor();
    }

    @Override
    public void saveVisitor(TbVisitor visitor) {
        visitorMapper.saveVisitor(visitor);
    }

    @Override
    public void updateVisitTimes(long id) {
        visitorMapper.updateVisitTimes(id);
    }

    @Override
    public TbVisitor findByIp(String ip) {
        return visitorMapper.findByIp(ip);
    }

    @Override
    public int countVisitorByIp(String ip) {
        return visitorMapper.countVisitorByIp(ip);
    }

    @Override
    public PageResult paginationVisitor(int page, int pageSize, TbVisitor visitor) {
        PageHelper.startPage(page, pageSize);
        Page<TbVisitor> page1 = (Page<TbVisitor>) visitorMapper.paginationByLocation(visitor);
        return new PageResult(page1.getTotal(), page1.getResult());
    }

    @Override
    public void incrementTimesByIp(String ip) {
        visitorMapper.updateByIp(ip);
    }

    @Override
    public int countByIp(long visitorId, long articleId) {
        return watchMapper.countByIp(visitorId, articleId);
    }

    @Override
    public void newWatch(long visitorId, long articleId) {
        watchMapper.newWatch(visitorId, articleId);
    }

    @Override
    public void updateInfo(TbVisitor visitor) {
        visitorMapper.updateInfo(visitor);
    }

    @Override
    public List<TbVisitor> listByEmail(String email) {
        return visitorMapper.listByEmail(email);
    }
}
