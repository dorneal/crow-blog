package per.neal.blog.dao;

import per.neal.blog.entity.TbAdmin;

/**
 * 博客mapper
 *
 * @author neal
 */
public interface BlogMapper {

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
}
