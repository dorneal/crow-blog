package per.neal.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz配置
 *
 * @author neal
 */
@Configuration
public class QuartzConfig {

    /**
     * 使用spring 管理factory
     * 才能在job中进行注入
     *
     * @return AdaptableJobFactory
     */
    @Bean(name = "jobFactory")
    public TimingFactory adaptableJobFactory() {
        return new TimingFactory();
    }

    /**
     * 在SchedulerFactoryBean注入自定义的jobFactory bean
     *
     * @return SchedulerFactoryBean
     */
    @Bean(name = "schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(adaptableJobFactory());
        return schedulerFactoryBean;
    }
}
