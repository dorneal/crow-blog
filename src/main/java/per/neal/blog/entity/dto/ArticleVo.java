package per.neal.blog.entity.dto;

import per.neal.blog.entity.TbArticle;

import java.io.Serializable;

/**
 * 文章包装类，用于前台显示
 *
 * @author neal
 */
public class ArticleVo implements Serializable {
    private static final long serialVersionUID = 8776620678358146789L;
    /**
     * 文章
     */
    private TbArticle article;
    /**
     * 栏目
     */
    private String column;

    public ArticleVo(TbArticle article, String column) {
        this.article = article;
        this.column = column;
    }

    public TbArticle getArticle() {
        return article;
    }

    public void setArticle(TbArticle article) {
        this.article = article;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }
}
