package per.neal.blog.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import per.neal.blog.dao.ArticleMapper;
import per.neal.blog.entity.ArticleModel;
import per.neal.blog.entity.TbArticle;
import per.neal.blog.repository.ArticleRepository;
import per.neal.blog.service.ArticleSearchService;

import javax.annotation.Resource;
import java.util.*;

/**
 * 文章搜索实现类
 *
 * @author neal
 */
@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {

    /**
     * 注入文章repository
     */
    @Resource
    private ArticleRepository articleRepository;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Page<ArticleModel> findByFields(String fields, Pageable pageable) {
        if (StringUtils.isEmpty(fields)) {
            return articleRepository.findAll(pageable);
        }
        return articleRepository.findByTitleContainingOrDescriptionContaining(fields, fields, pageable);
    }

    @Override
    public void reIndex() {
        List<TbArticle> list = articleMapper.listArticle();
        List<ArticleModel> models = new LinkedList<>();
        ArticleModel model;
        for (TbArticle article : list) {
            // 只存状态为已发布的文章
            if (article.getArticleStatus() == 1) {
                model = new ArticleModel();
                model.setId(article.getId());
                model.setCreateTime(article.getCreateTime());
                model.setColumnId(article.getColumnId());
                model.setDescription(article.getDescription());
                model.setTitle(article.getTitle());
                models.add(model);
            }
        }
        articleRepository.saveAll(models);
    }
}
