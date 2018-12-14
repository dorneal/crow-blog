package per.neal.blog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import per.neal.blog.constant.SearchableDefinition;

import java.util.Date;

/**
 * 搜索索引model
 *
 * @author neal
 */
@SolrDocument(solrCoreName = "crow_blog")
public class ArticleModel implements SearchableDefinition {
    @Id
    @Indexed
    private long id;

    @Indexed(name = TITLE_FIELD_NAME, type = "text_ik")
    private String title;

    @Indexed(COLUMN_ID_FIELD_NAME)
    private long columnId;

    @Indexed(CREATE_TIME_FIELD_NAME)
    private Date createTime;

    @Indexed(name = DESCRIPTION_FIELD_NAME, type = "text_ik")
    private String description;

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

    public long getColumnId() {
        return columnId;
    }

    public void setColumnId(long columnId) {
        this.columnId = columnId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ArticleModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", columnId=" + columnId +
                ", createTime=" + createTime +
                ", description='" + description + '\'' +
                '}';
    }
}
