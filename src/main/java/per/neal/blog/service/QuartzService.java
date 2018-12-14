package per.neal.blog.service;

import org.quartz.*;
import org.springframework.stereotype.Service;
import per.neal.blog.task.TimingPublishTask;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 定时发布service
 *
 * @author neal
 */
@Service("quartzService")
public class QuartzService {

    @Resource
    private Scheduler scheduler;

    /**
     * 任务公开名
     */
    private static final String JOB_PUB_NAME = "timing2publish";
    /**
     * 任务公开属组
     */
    private static final String JOB_PUB_GROUP_NAME = "publishJobGroup";
    /**
     * 触发器公开名
     */
    private static final String TRIGGER_PUB_NAME = "timingPublishTrigger";
    /**
     * 触发器公开属组
     */
    private static final String TRIGGER_PUB_GROUP_NAME = "publishTriggerGroup";


    /**
     * 执行
     *
     * @param time      时间戳
     * @param articleId 文章ID
     * @throws SchedulerException SchedulerException
     */
    public void addJob(long time, long articleId) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(TimingPublishTask.class)
                .withIdentity(JOB_PUB_NAME + articleId, JOB_PUB_GROUP_NAME)
                // 将文章ID传递给job
                .usingJobData("id", articleId)
                .build();

        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInHours(0)
                // 不重复执行
                .withRepeatCount(0);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TRIGGER_PUB_NAME + articleId, TRIGGER_PUB_GROUP_NAME)
                .withSchedule(simpleScheduleBuilder)
                // 现在开始（从现在开始到间隔时间结束）
                .startAt(new Date(time))
                .endAt(null)
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    /**
     * 删除任务
     *
     * @param articleId 文章ID
     */
    public boolean deleteJob(long articleId) {
        JobKey jobKey = JobKey.jobKey(JOB_PUB_NAME + articleId, JOB_PUB_GROUP_NAME);
        try {
            scheduler.deleteJob(jobKey);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 更改job 执行时间
     * TODO 存在bug，如果异常时应该将数据库数据状态改为异常
     *
     * @param time      时间
     * @param articleId 文章ID
     * @return 是否更改成功
     */
    public boolean updateJobWorkTime(long time, long articleId) {
        TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_PUB_NAME + articleId, TRIGGER_PUB_GROUP_NAME);
        try {
            Trigger trigger = scheduler.getTrigger(triggerKey)
                    .getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .startAt(new Date(time))
                    .endAt(null)
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }
}
