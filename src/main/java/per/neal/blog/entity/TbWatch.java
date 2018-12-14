package per.neal.blog.entity;


import java.io.Serializable;

/**
 * 文章浏览表实体类
 *
 * @author neal
 */
public class TbWatch implements Serializable {

    private static final long serialVersionUID = 1648818912938873375L;
    private long id;
    /**
     * 文章ID
     */
    private long articleId;
    /**
     * 来访者ID
     */
    private long visitorId;


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


    public long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorId = visitorId;
    }

}
