package per.neal.blog.task;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import per.neal.blog.constant.Constants;
import per.neal.blog.entity.TbTiming;
import per.neal.blog.service.ArticleService;

import javax.annotation.Resource;

/**
 * 定时任务
 *
 * @author neal
 */
public class TimingPublishTask extends QuartzJobBean {

    /**
     * 必须自定义JobFactory，使用spring进行JOB实例的创建，否则无法注入成功
     */
    @Resource
    private ArticleService articleService;

    /**
     * 将该文章的状态改为启用
     *
     * @param jobExecutionContext JobExecutionContext
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        long articleId = dataMap.getLong("id");
        articleService.enableArticle(articleId);
        // 更新数据库
        TbTiming timing = new TbTiming();
        timing.setArticleId(articleId);
        timing.setWorkStatus(Constants.JOB_WORK_HAS_STATUS + "");
        articleService.updateTiming(timing);
    }
}
