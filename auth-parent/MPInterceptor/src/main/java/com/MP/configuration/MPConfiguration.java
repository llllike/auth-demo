package com.MP.configuration;


import com.MP.properties.MPProperties;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
@EnableConfigurationProperties(value = {
        MPProperties.class
})
public class MPConfiguration {
    @Bean
    public MybatisPlusInterceptor mpInterceptor() {
        MPProperties mpProperties = new MPProperties();
        //1.定义Mp拦截器
        MybatisPlusInterceptor mpInterceptor = new MybatisPlusInterceptor();
        if (mpProperties.getPage().equals("true")){
            //2.添加分页的拦截器
            mpInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        }
        if (mpProperties.getLocker().equals("true")){
            //3.添加乐观锁拦截器
            mpInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        }
        return mpInterceptor;
    }
}
