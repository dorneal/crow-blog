package per.neal.blog.entity.dto;

import per.neal.blog.entity.TbArticle;

import java.io.Serializable;

/**
 * 文章包装类
 *
 * @author neal
 */
public class Articles implements Serializable {
    private static final long serialVersionUID = -2101601447035924485L;
    /**
     * 文章
     */
    private TbArticle article;
    /**
     * 所属栏目
     */
    private String columnName;
    /**
     * 点赞数
     */
    private int like;
    /**
     * 评论数
     */
    private int comment;
    /**
     * 浏览次数
     */
    private long browse;

    public TbArticle getArticle() {
        return article;
    }

    public void setArticle(TbArticle article) {
        this.article = article;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comments) {
        this.comment = comments;
    }

    public long getBrowse() {
        return browse;
    }

    public void setBrowse(long browse) {
        this.browse = browse;
    }
}
