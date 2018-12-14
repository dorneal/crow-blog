package per.neal.blog.entity;


import java.io.Serializable;

/**
 * 栏目实体
 *
 * @author neal
 */
public class TbColumn implements Serializable {

    private static final long serialVersionUID = 8525733743176447058L;
    private long id;
    /**
     * 上级栏目ID
     */
    private long higherId;

    /**
     * 栏目级别
     */
    private String columnLevel;
    /**
     * 栏目名
     */
    private String columnName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getHigherId() {
        return higherId;
    }

    public void setHigherId(long higherId) {
        this.higherId = higherId;
    }


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnLevel() {
        return columnLevel;
    }

    public void setColumnLevel(String columnLevel) {
        this.columnLevel = columnLevel;
    }
}
