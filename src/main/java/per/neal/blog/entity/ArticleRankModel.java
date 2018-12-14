package per.neal.blog.entity;


import java.io.Serializable;

/**
 * 用于首页显示文章排行的Model
 *
 * @author neal
 */
public class ArticleRankModel implements Serializable {
    private static final long serialVersionUID = -6122400344355609173L;
    private long id;
    private String title;
    private int articleCount;

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

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }
}
