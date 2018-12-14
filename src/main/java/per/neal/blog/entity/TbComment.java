package per.neal.blog.entity;


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 评论表
 *
 * @author neal
 */
public class TbComment implements Serializable {

    private static final long serialVersionUID = -4074095790020625919L;
    private long id;

    /**
     * 上级评论ID
     */
    private long higherId;

    /**
     * 文章ID
     */
    private long articleId;

    /**
     * 发布时间
     */
    private java.sql.Timestamp commentTime;

    /**
     * 来访者ID
     */
    private long visitorId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论者名称
     */
    private String commentName;

    /**
     * 评论者邮箱
     */
    private String commentEmail;


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


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentEmail() {
        return commentEmail;
    }

    public void setCommentEmail(String commentEmail) {
        this.commentEmail = commentEmail;
    }
}
