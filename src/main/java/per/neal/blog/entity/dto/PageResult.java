package per.neal.blog.entity.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页包装类
 *
 * @author neal
 */
public class PageResult implements Serializable {
    private static final long serialVersionUID = -5944453017899257892L;
    /**
     * 总条数
     */
    private long total;
    /**
     * 数据
     */
    private List<?> rows;

    public PageResult(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
