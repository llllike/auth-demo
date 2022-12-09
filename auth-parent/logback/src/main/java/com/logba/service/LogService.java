package com.logba.service;

import ch.qos.logback.classic.Level;
import com.logba.entity.OptLogDTO;
import com.logba.properties.logbackProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;


@Service
@Slf4j
public class LogService {
    private static Connection connection=null;
    //将日志信息保存到数据库
    public void saveLog(OptLogDTO optLogDTO, logbackProperties properties){
        //此处只是将日志信息进行输出，实际项目中可以将日志信息保存到数据库
        ch.qos.logback.classic.Logger logger =(ch.qos.logback.classic.Logger) LoggerFactory.getLogger(properties.getLoggerName());
        switch (properties.getLevel()){
            case "info":
                logger.setLevel(Level.INFO);
                break;
            case "debug":
                logger.setLevel(Level.DEBUG);
                break;
            case "warn":
                logger.setLevel(Level.WARN);
                break;
            case "error":
                logger.setLevel(Level.ERROR);
                break;
            default:
                logger.setLevel(Level.INFO);
                break;
        }
        logger.info(properties.getMsg()+optLogDTO);
        logbackProperties.Datasource datasource = properties.getDatasource();
        if (Objects.equals(datasource.getSave(), "true")){
            Connection connection = LogService.getConnection(datasource);
            PreparedStatement ps = null;
            try {
                ps = connection.prepareStatement(datasource.getSQL());
                ps.setString(1,optLogDTO.getRequestIp());
                ps.setString(2,optLogDTO.getType());
                ps.setString(3,optLogDTO.getUserName());
                ps.setString(4,optLogDTO.getDescription());
                ps.setString(5,optLogDTO.getClassPath());
                ps.setString(6,optLogDTO.getActionMethod());
                ps.setString(7,optLogDTO.getRequestUri());
                ps.setString(8,optLogDTO.getHttpMethod());
                ps.setString(9,optLogDTO.getParams());
                ps.setString(10,optLogDTO.getResult());
                ps.setString(11,optLogDTO.getExDesc());
                ps.setString(12,optLogDTO.getExDetail());
                ps.setString(13,optLogDTO.getStartTime().toString());
                ps.setString(14,optLogDTO.getFinishTime().toString());
                ps.setLong(15,optLogDTO.getConsumingTime());
                ps.setString(16,optLogDTO.getUa());
                ps.executeUpdate();
                LogService.closeCon();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
    // 返回数据库连接实例
    public static Connection getConnection(logbackProperties.Datasource datasource){
        try {

            //加载驱动
            Class.forName(datasource.getDriverClassName());
            //建立和数据库的连接
            connection= DriverManager.getConnection(datasource.getUrl(),datasource.getUsername(),datasource.getPassword());
        }catch(Exception e) {
            e.printStackTrace();
        }finally{
            return connection;
        }
    }
    // 关闭数据库连接
    public static void closeCon(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
