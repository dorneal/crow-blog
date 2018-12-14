package per.neal.blog.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.neal.blog.dao.ColumnMapper;
import per.neal.blog.entity.ColumnRankModel;
import per.neal.blog.entity.TbColumn;
import per.neal.blog.service.ColumnService;

import javax.annotation.Resource;
import java.util.List;

/**
 * columnService实现类
 *
 * @author neal
 */
@Service("columnService")
public class ColumnServiceImpl implements ColumnService {

    @Resource
    private ColumnMapper columnMapper;

    @Override
    public List<TbColumn> listColumn() {
        return columnMapper.listColumn();
    }

    @Override
    public TbColumn findById(long id) {
        return columnMapper.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveColumn(TbColumn column) {
        columnMapper.saveColumn(column);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateColumn(TbColumn column) {
        columnMapper.updateColumn(column);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        columnMapper.deleteById(id);
    }

    @Override
    public int countByName(String columnName) {
        return columnMapper.countByName(columnName);
    }

    @Override
    public List<TbColumn> listByLevel(String columnLevel) {
        return columnMapper.listByLevel(columnLevel);
    }

    @Override
    public List<TbColumn> listByParentId(long parentId) {
        return columnMapper.listByParentId(parentId);
    }

    @Override
    @Cacheable(cacheNames = "rank", key = "'rankByColumn'")
    public List<ColumnRankModel> columnRank() {
        return columnMapper.columnRank();
    }

    @Override
    @Cacheable(cacheNames = "column")
    public String columnNav(long id) {
        return formatColumn(id);
    }


    /**
     * 递归求栏目路径,用于前台显示
     *
     * @param columnId 栏目
     * @return 栏目路径
     */
    private String formatColumn(long columnId) {
        TbColumn column = columnMapper.findById(columnId);
        if (column != null) {
            return String.format("%s<li class='breadcrumb-item'><a href='javascript:void(0)' onclick='indexColumnNav(%d)'>%s</a></li>",
                    formatColumn(column.getHigherId()), column.getId(), column.getColumnName());
        }
        return "";
    }
}
