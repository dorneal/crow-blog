package per.neal.blog.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * 自定义的job factory
 * 使用spring管理job创建，代替quartz管理（job中注入了service，必须使用spring管理注入）
 *
 * @author neal
 */
public class TimingFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object obj = super.createJobInstance(bundle);
        capableBeanFactory.autowireBean(obj);
        return obj;
    }
}
