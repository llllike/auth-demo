package com.trans.config;

import com.trans.properties.UserProperties;
import com.trans.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.concurrent.ScheduledThreadPoolExecutor;



@EnableConfigurationProperties(value = {
        UserProperties.class,
})
@Configuration
public class emailConfiguration {
    @Autowired
    JavaMailSender javaMailSender;
    @Bean
    public EmailUtil getEmailUtil(UserProperties userProperties) {
        return new EmailUtil(javaMailSender, userProperties,getScheduledThreadPool());
    }

    /**
     * 注册线程池
     * @return
     */
    @Bean
    public ScheduledThreadPoolExecutor getScheduledThreadPool(){
        UserProperties userProperties=new UserProperties();
        return new ScheduledThreadPoolExecutor(userProperties.getCorePoolSize());
    }

}
