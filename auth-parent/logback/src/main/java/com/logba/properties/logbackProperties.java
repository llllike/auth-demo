package com.logba.properties;


import ch.qos.logback.classic.Level;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDateTime;


/**
 * 日志配置消息
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "logback")
public class logbackProperties {
    private Datasource datasource= new Datasource();
    @Data
    public static class Datasource{
        private String url="jdbc:mysql://127.0.0.1:3306/logback?useUnicode=true&characterEncodeing=UTF-8&useSSL=false&serverTimezone=GMT";
        private String username="root";
        private String password="root";
        private String driverClassName="com.mysql.cj.jdbc.Driver";
        private String save="false";
        private String SQL="insert into logback.optlogdto values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    }

    // 日志等级
    private String level="info";
    // 日志名称
    private String loggerName="com.logback";
    // 日志描述
    private String msg="保存日志信息: ";


}
