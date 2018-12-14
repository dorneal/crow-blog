package per.neal.blog.entity;

import java.io.Serializable;

/**
 * 属性类查询
 *
 * @author neal
 */
public class ColumnRankModel implements Serializable {
    private static final long serialVersionUID = 8678663009158425548L;
    private long id;
    private String columnName;
    private int columnCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
}
