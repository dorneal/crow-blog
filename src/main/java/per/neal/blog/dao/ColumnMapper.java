package per.neal.blog.dao;

import per.neal.blog.entity.ColumnRankModel;
import per.neal.blog.entity.TbColumn;

import java.util.List;

/**
 * 类别mapper
 *
 * @author neal
 */
public interface ColumnMapper {
    /**
     * 查询所有类别
     *
     * @return list
     */
    List<TbColumn> listColumn();

    /**
     * 根据ID查找栏目
     *
     * @param id ID
     * @return TbColumn
     */
    TbColumn findById(long id);

    /**
     * 新增一个栏目
     *
     * @param column TbColumn
     */
    void saveColumn(TbColumn column);


    /**
     * 更新一个栏目
     *
     * @param column TbColumn
     */
    void updateColumn(TbColumn column);

    /**
     * 根据ID删除栏目
     *
     * @param id ID
     */
    void deleteById(long id);

    /**
     * 统计该栏目名是否被占用
     *
     * @param columnName 栏目名称
     * @return count
     */
    int countByName(String columnName);

    /**
     * 根据级别查找
     *
     * @param columnLevel 栏目级别
     * @return list
     */
    List<TbColumn> listByLevel(String columnLevel);

    /**
     * 根据父级栏目ID查询子级栏目列表
     *
     * @param parentId 父级栏目
     * @return list
     */
    List<TbColumn> listByParentId(long parentId);

    /**
     * 查询栏目，以及栏目下的文章数
     *
     * @return list
     */
    List<ColumnRankModel> columnRank();
}
