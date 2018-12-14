package per.neal.blog.dao;

import org.apache.ibatis.annotations.Param;
import per.neal.blog.entity.TbVisitor;

import java.util.List;

/**
 * 来访者mapper
 *
 * @author neal
 */
public interface VisitorMapper {
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
     * 根据IP去更新
     *
     * @param ip String
     */
    void updateByIp(@Param("ip") String ip);

    /**
     * 根据ip查找来访者
     *
     * @param ip ip
     * @return TbVisitor
     */
    TbVisitor findByIp(@Param("ip") String ip);

    /**
     * 统计该来访者是否来访过
     *
     * @param ip ip地址
     * @return int
     */
    int countVisitorByIp(String ip);

    /**
     * 根据地址查询分页
     *
     * @param visitor TbVisitor
     * @return list
     */
    List<TbVisitor> paginationByLocation(TbVisitor visitor);

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
