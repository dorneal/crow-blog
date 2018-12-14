package per.neal.blog.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import per.neal.blog.dao.*;
import per.neal.blog.entity.*;
import per.neal.blog.entity.dto.ArticleVo;
import per.neal.blog.entity.dto.Articles;
import per.neal.blog.entity.dto.PageResult;
import per.neal.blog.repository.ArticleRepository;
import per.neal.blog.service.ArticleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文章service实现类
 *
 * @author neal
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ColumnMapper columnMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private LikeMapper likeMapper;
    @Resource
    private WatchMapper watchMapper;
    @Resource
    private TimingMapper timingMapper;
    @Resource
    private ArticleRepository articleRepository;

    @Override
    public void updateTiming(TbTiming tbTiming) {
        timingMapper.updateTiming(tbTiming);
    }

    @Override
    public void saveTiming(TbTiming tbTiming) {
        timingMapper.saveTiming(tbTiming);
    }

    @Override
    public TbTiming findByArticleId(long id) {
        return timingMapper.findByArticleId(id);
    }

    @Override
    public TbArticle findArticleById(long id) {
        return articleMapper.findArticleById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(TbArticle article) {
        updateSearchIndex(article);
        articleMapper.updateArticle(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newArticle(TbArticle article) {
        articleMapper.newArticle(article);
        // 拿到插入后的文章ID
        updateSearchIndex(article);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchRemove(long[] ids) {
        Arrays.stream(ids).forEach((id) -> {
            articleMapper.removeArticle(id);
            // 删除搜索索引
            articleRepository.deleteById(id + "");
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableArticle(long id) {
        updateSearchIndex(articleMapper.findArticleById(id));
        articleMapper.enableArticle(id);
    }

    @Override
    public int countByTitle(String title) {
        return articleMapper.countByTitle(title);
    }

    @Override
    @Cacheable(cacheNames = "articles")
    public List<TbArticle> listByColumnId(long columnId) {
        return articleMapper.listByColumnId(columnId);
    }

    @Override
    @Cacheable(cacheNames = "articles", key = "'newestArticle'")
    public List<TbArticle> newestArticle() {
        return articleMapper.newestArticle();
    }

    @Override
    public PageResult articles(int page, int pageSize, TbArticle article) {
        int offset = (page > 0 ? (page - 1) : 0) * pageSize;
        List<TbArticle> list;
        int total;
        if (article != null && article.getTitle() != null && !"".equals(article.getTitle())) {
            list = articleMapper.paginationByCondition(offset, pageSize, article.getTitle());
            total = articleMapper.countArticleByKeywords(article.getTitle());
        } else {
            list = articleMapper.pagination(offset, pageSize);
            total = articleMapper.countArticle();
        }
        final List<Articles> articlesList = new ArrayList<>(list.size());
        list.forEach((x) -> {
            Articles articles = new Articles();
            articles.setArticle(x);
            long id = x.getId();
            articles.setColumnName(formatColumnForEnd(x.getColumnId()));
            articles.setBrowse(watchMapper.countByArticleId(id));
            articles.setComment(commentMapper.countByArticleId(id));
            articles.setLike(likeMapper.countLikeByArticleId(id));
            articlesList.add(articles);
        });
        return new PageResult(total, articlesList);
    }


    @Override
    @Cacheable(cacheNames = "rank", key = "'rankByVisit'")
    public List<ArticleRankModel> rankByVisit() {
        return articleMapper.rankByVisit();
    }

    @Override
    @Cacheable(cacheNames = "rank", key = "'rankByLike'")
    public List<ArticleRankModel> rankByLike() {
        return articleMapper.rankByLike();
    }

    @Override
    @Cacheable(cacheNames = "article")
    public ArticleVo article(long id) {
        TbArticle article = articleMapper.findArticleById(id);
        return new ArticleVo(
                // 1. 查询该文章
                article,
                // 2.查询该文章的所属栏目
                formatColumnForFront(article.getColumnId()) + "<li class='breadcrumb-item active' aria-current='page'>" + article.getTitle() + "</li>");
    }


    @Override
    public int countLikeByVisitor(long visitorId, long articleId) {
        return likeMapper.countLikeByVisitor(visitorId, articleId);
    }

    @Override
    public void saveLike(long visitorId, long articleId) {
        likeMapper.newLike(visitorId, articleId);
    }

    @Override
    public void cancelLike(long visitorId, long articleId) {
        likeMapper.cancelLike(visitorId, articleId);
    }

    /**
     * 递归求栏目路径，用于后台显示
     *
     * @param columnId 栏目
     * @return 栏目路径
     */
    private String formatColumnForEnd(long columnId) {
        TbColumn column = columnMapper.findById(columnId);
        if (column != null) {
            return formatColumnForEnd(column.getHigherId()) + " / " + column.getColumnName();
        }
        return "";
    }

    /**
     * 递归求栏目路径,用于前台显示
     *
     * @param columnId 栏目
     * @return 栏目路径
     */
    private String formatColumnForFront(long columnId) {
        TbColumn column = columnMapper.findById(columnId);
        if (column != null) {
            return String.format("%s<li class='breadcrumb-item'><a onclick='toIndex(%d)' href='javascript:void(0);'>%s</a></li>",
                    formatColumnForFront(column.getHigherId()), column.getId(), column.getColumnName());
        }
        return "";
    }

    /**
     * 更新搜索索引
     *
     * @param article TbArticle
     */
    private void updateSearchIndex(TbArticle article) {
        // 只有当文章状态为发布时，才进行更新索引
        if (article.getArticleStatus() == 1) {
            ArticleModel model = new ArticleModel();
            model.setTitle(article.getTitle());
            model.setDescription(article.getDescription());
            model.setColumnId(article.getColumnId());
            model.setCreateTime(article.getCreateTime());
            model.setId(article.getId());
            articleRepository.save(model);
        }
    }
}
