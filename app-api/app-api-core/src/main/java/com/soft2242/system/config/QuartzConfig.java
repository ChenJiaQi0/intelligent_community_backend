package com.soft2242.system.config;

import com.soft2242.system.util.QuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    // 创建一个JobDetail(工作详情)类对象,保存到Spring容器中，这个类用于封装我们编写的job接口实现类
    @Bean
    public JobDetail jobDetail(){
        System.out.println("showTime方法运行");
        return JobBuilder.newJob(QuartzJob.class)   // 绑定要运行的任务类的类对象
                .withIdentity("job")               // 设置job的名称
                .storeDurably()                     // 信息持久
                // 设置storeDurably之后,当没有触发器指向这个JobDetail时,JobDetail也不会从
                // Spring容器中删除,如果不设置这行,就会自动从Spring容器中删除
                .build();
    }

    // 声明触发器，触发器决定我们的工作\任务何时触发
    @Bean
    public Trigger trigger(){
        System.out.println("showTime触发器运行");

        // 定义Cron表达式，每分钟触发一次
        CronScheduleBuilder cronScheduleBuilder =
                CronScheduleBuilder.cronSchedule("0 0 */1 * * ?");

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail()) // 绑定JobDetail对象
                .withIdentity("trigger") // 定义触发器名称
                .withSchedule(cronScheduleBuilder) // 绑定Cron表达式
                .build();
    }
}
