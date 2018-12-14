package per.neal.blog.entity;


import java.io.Serializable;

/**
 * 定时发布文章表
 *
 * @author neal
 */
public class TbTiming implements Serializable {

    private static final long serialVersionUID = 7758261046555585980L;
    private long id;
    /**
     * 文章ID
     */
    private long articleId;
    /**
     * 执行时间
     */
    private java.sql.Timestamp workTime;

    /**
     * 发布状态，1待发布，2已发布，0异常
     */
    private String workStatus;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }


    public java.sql.Timestamp getWorkTime() {
        return workTime;
    }

    public void setWorkTime(java.sql.Timestamp workTime) {
        this.workTime = workTime;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }
}
