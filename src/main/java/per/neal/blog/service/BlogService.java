package per.neal.blog.service;

import per.neal.blog.entity.TbAdmin;
import per.neal.blog.entity.TbSite;


/**
 * 博客service
 *
 * @author neal
 */
public interface BlogService {
    /**
     * 根据用户名查找
     *
     * @param username 用户名
     * @return TbAdmin
     */
    TbAdmin findByName(String username);

    /**
     * 更新信息
     *
     * @param admin TbAdmin
     */
    void updateInfo(TbAdmin admin);

    /**
     * 查询公开信息
     *
     * @return TbAdmin
     */
    TbAdmin find();


    /**
     * 统计文章数
     *
     * @return 总文章数
     */
    long articleCount();

    /**
     * 统计评论数
     *
     * @return 总评论数
     */
    long commentCount();

    /**
     * 统计来访者
     *
     * @return 总来访者数
     */
    long visitorCount();

    /**
     * 统计栏目
     *
     * @return 总栏目数
     */
    long columnCount();

    /**
     * 统计总点赞数
     *
     * @return 总点赞数
     */
    long likeCount();

    /**
     * 统计所有的
     *
     * @return 统计所有站点属性
     */
    TbSite countAll();

    /**
     * 更新count
     *
     * @param site 站点count
     */
    void updateCount(TbSite site);
}
