package com.logba.config;


import com.logba.aspect.SysLogAspect;
import com.logba.event.SysLogListener;
import com.logba.properties.logbackProperties;
import com.logba.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
@EnableConfigurationProperties(value = {
        logbackProperties.class,
})
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SysLogAspect sysLogAspect() {
        return new SysLogAspect();
    }

    // 配置监听方法
    @Bean
    @ConditionalOnMissingBean
    public SysLogListener sysLogListener(LogService logService,logbackProperties properties){
        return new SysLogListener(optLogDTO -> logService.saveLog(optLogDTO,properties));
    }



}
