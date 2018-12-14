package per.neal.blog.entity;


import java.io.Serializable;

/**
 * 站点统计表
 *
 * @author neal
 */
public class TbSite implements Serializable {

    private static final long serialVersionUID = 7382825879676172441L;
    private long id;
    /**
     * 来访者统计
     */
    private long visitCount;
    /**
     * 文章统计
     */
    private long articleCount;
    /**
     * 评论统计
     */
    private long commentCount;
    /**
     * 点赞统计
     */
    private long likeCount;
    /**
     * 分类统计
     */
    private long tagsCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(long visitCount) {
        this.visitCount = visitCount;
    }


    public long getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(long articleCount) {
        this.articleCount = articleCount;
    }


    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }


    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }


    public long getTagsCount() {
        return tagsCount;
    }

    public void setTagsCount(long tagsCount) {
        this.tagsCount = tagsCount;
    }

    @Override
    public String toString() {
        return "TbSite{" +
                "id=" + id +
                ", visitCount=" + visitCount +
                ", articleCount=" + articleCount +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                ", tagsCount=" + tagsCount +
                '}';
    }
}
