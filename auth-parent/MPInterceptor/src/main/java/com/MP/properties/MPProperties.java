package com.MP.properties;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "mybatis.plus.interceptor")
public class MPProperties {
    // 分页拦截器
    private String page="true";
    // 乐观锁拦截器
    private String locker="true";
}
