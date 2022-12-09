package com.trans.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;



@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "spring.mail")
public class UserProperties {
    // 发件人
    private String username;
    // 核心线程数量
    private Integer corePoolSize=10;

}
