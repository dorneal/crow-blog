package per.neal.blog.entity;


import java.io.Serializable;

/**
 * 文章实体类
 *
 * @author neal
 */
public class TbArticle implements Serializable {

    private static final long serialVersionUID = -4804042667047470817L;
    private long id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章简介
     */
    private String description;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 创建时间
     */
    private java.sql.Timestamp createTime;
    /**
     * 所属类别
     */
    private long columnId;
    /**
     * 文章状态（停用，启用，延迟发布）
     */
    private long articleStatus;
    /**
     * 文章标签（转载，原创）
     */
    private long articleTag;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public long getColumnId() {
        return columnId;
    }

    public void setColumnId(long columnId) {
        this.columnId = columnId;
    }


    public long getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(long articleStatus) {
        this.articleStatus = articleStatus;
    }


    public long getArticleTag() {
        return articleTag;
    }

    public void setArticleTag(long articleTag) {
        this.articleTag = articleTag;
    }

    @Override
    public String toString() {
        return "TbArticle{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", columnId=" + columnId +
                ", articleStatus=" + articleStatus +
                ", articleTag=" + articleTag +
                '}';
    }
}
