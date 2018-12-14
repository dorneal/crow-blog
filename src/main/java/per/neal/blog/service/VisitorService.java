package per.neal.blog.service;

import org.apache.ibatis.annotations.Param;
import per.neal.blog.entity.TbVisitor;
import per.neal.blog.entity.dto.PageResult;

import java.util.List;

/**
 * 来访者service接口
 *
 * @author neal
 */
public interface VisitorService {
    /**
     * 查询所有的来访者
     *
     * @return list
     */
    List<TbVisitor> listVisitor();

    /**
     * 新增来访者
     *
     * @param visitor TbVisitor
     */
    void saveVisitor(TbVisitor visitor);

    /**
     * 更新来访者来访次数
     *
     * @param id TbVisitor
     */
    void updateVisitTimes(long id);

    /**
     * 根据ip查找来访者
     *
     * @param ip ip
     * @return TbVisitor
     */
    TbVisitor findByIp(String ip);

    /**
     * 统计该来访者是否来访过
     *
     * @param ip ip地址
     * @return int
     */
    int countVisitorByIp(String ip);

    /**
     * 分页搜索显示来访者
     *
     * @param page     当前页
     * @param pageSize 页面显示数量
     * @param visitor  来访者
     * @return list
     */
    PageResult paginationVisitor(int page, int pageSize, TbVisitor visitor);

    /**
     * 根据IP去更新
     *
     * @param ip String
     */
    void incrementTimesByIp(String ip);

    /**
     * 查询该访客是否浏览过该文章
     *
     * @param visitorId 访客ID
     * @param articleId 文章ID
     * @return count
     */
    int countByIp(long visitorId, long articleId);

    /**
     * 插入浏览记录
     *
     * @param visitorId 访问者ID
     * @param articleId 文章ID
     */
    void newWatch(long visitorId, long articleId);

    /**
     * 如果评论时填写过信息，则将该信息填写至来访表
     *
     * @param visitor TbVisitor
     */
    void updateInfo(TbVisitor visitor);

    /**
     * 根据邮箱查找来访者
     *
     * @param email email
     * @return list
     */
    List<TbVisitor> listByEmail(@Param("email") String email);

}
