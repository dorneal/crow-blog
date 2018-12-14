package per.neal.blog.entity.dto;

import per.neal.blog.entity.TbArticle;
import per.neal.blog.entity.TbTiming;

import java.io.Serializable;

/**
 * @author neal
 */
public class ArticleTiming implements Serializable {
    private static final long serialVersionUID = 4308447858705126919L;
    /**
     * 文章
     */
    private TbArticle article;
    /**
     * 发布信息类
     */
    private TbTiming timing;

    public TbArticle getArticle() {
        return article;
    }

    public void setArticle(TbArticle article) {
        this.article = article;
    }

    public TbTiming getTiming() {
        return timing;
    }

    public void setTiming(TbTiming timing) {
        this.timing = timing;
    }
}
