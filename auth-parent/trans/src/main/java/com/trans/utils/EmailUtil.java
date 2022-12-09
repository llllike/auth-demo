package com.trans.utils;


import com.trans.entity.Sender;
import com.trans.properties.UserProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class EmailUtil {
    private JavaMailSender javaMailSender;
    private UserProperties userProperties;
    private ScheduledThreadPoolExecutor threadPoolExecutor;
    public boolean send(Sender sender){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(sender.getSubject());
        message.setFrom(userProperties.getUsername());
        message.setSentDate(new Date());
        message.setTo(sender.getTo());
        message.setText(sender.getContent());
        javaMailSender.send(message);
        return true;
    }
    public boolean sendSetTime(Sender sender,Date date){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender);
            }
        };
        long now = new Date().getTime();
        long time = date.getTime();
        threadPoolExecutor.schedule(runnable,time-now,TimeUnit.MILLISECONDS);
        return true;
    }
    public boolean sendSetTime(Sender sender,Long time,TimeUnit timeUnit){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender);
            }
        };
        threadPoolExecutor.schedule(runnable,time,timeUnit);
        return true;
    }
    public boolean sendSetTime(Sender sender,Long time){
        return this.sendSetTime(sender,time,TimeUnit.SECONDS);
    }
    public boolean sendSetTimeCycle(Sender sender,Date date,Long cycleTimeSeconds){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender);
            }
        };
        long now = new Date().getTime();
        long time = date.getTime();
        threadPoolExecutor.scheduleWithFixedDelay(runnable,time-now,cycleTimeSeconds,TimeUnit.MILLISECONDS);
        return true;
    }
    public boolean sendSetTimeCycle(Sender sender,Long time,TimeUnit timeUnit,Long cycleTime){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender);
            }
        };
        threadPoolExecutor.scheduleWithFixedDelay(runnable,time,cycleTime,timeUnit);
        return true;
    }
    public boolean sendSetTimeCycle(Sender sender,Long time,Long cycleTimeSeconds){
        return this.sendSetTimeCycle(sender,time,TimeUnit.SECONDS,cycleTimeSeconds);
    }
    public boolean send(Sender sender, MultipartFile...files){
        MimeMessage message =javaMailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(userProperties.getUsername()));
            message.setSentDate(new Date());
            message.setText(sender.getContent());
            //邮件收件人；TO表示主要接收人，CC表示抄送人，BCC表示秘密抄送人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sender.getTo()[0]));
            message.setSubject(sender.getSubject());
            if (files.length!=0){
                MimeBodyPart mimeBodyPart=new MimeBodyPart();
                MimeMultipart mimeMultipart=new MimeMultipart();
                mimeBodyPart.setContent(sender.getContent(), "text/html;charset=UTF-8");
                mimeMultipart.setSubType("related");
                //添加文本进入mimeMultipart
                mimeMultipart.addBodyPart(mimeBodyPart);
                for(MultipartFile file:files) {
                    File file2=null;
                    //这一步是将MultipartFile转化为File文件
                    //createTempFile(文件前缀, 文件后缀)
                    file2=File.createTempFile("wan", cutout(file));
                    file.transferTo(file2);
                    file2.deleteOnExit();
                    //将附件添加进入mimeMultipart
                    MimeBodyPart attachPart=new MimeBodyPart();
                    attachPart.attachFile(file2);
                    mimeMultipart.addBodyPart(attachPart);
                }
                //将内容添加进邮件
                message.setContent(mimeMultipart);
                //保存邮件
                message.saveChanges();
            }else {
                message.setContent(sender.getContent(),"text/html;charset=UTF-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        javaMailSender.send(message);
        return true;
    }
    public boolean sendSetTime(Sender sender,MultipartFile[]files,Date date){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,files);
            }
        };
        long now = new Date().getTime();
        long time = date.getTime();
        threadPoolExecutor.schedule(runnable,time-now,TimeUnit.MILLISECONDS);
        return true;
    }
    public boolean sendSetTime(Sender sender,MultipartFile[]files,Long time,TimeUnit timeUnit){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,files);
            }
        };
        threadPoolExecutor.schedule(runnable,time,timeUnit);
        return true;
    }
    public boolean sendSetTime(Sender sender,MultipartFile[]files,Long time){
        return this.sendSetTime(sender,files,time,TimeUnit.SECONDS);
    }
    public boolean sendSetTimeCycle(Sender sender,MultipartFile[]files,Date date,Long cycleTimeSeconds){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,files);
            }
        };
        long now = new Date().getTime();
        long time = date.getTime();
        threadPoolExecutor.scheduleWithFixedDelay(runnable,time-now,cycleTimeSeconds,TimeUnit.MILLISECONDS);
        return true;
    }
    public boolean sendSetTimeCycle(Sender sender,MultipartFile[]files,Long time,TimeUnit timeUnit,Long cycleTime){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,files);
            }
        };
        threadPoolExecutor.scheduleWithFixedDelay(runnable,time,cycleTime,timeUnit);
        return true;
    }
    public boolean sendSetTimeCycle(Sender sender,MultipartFile[]files,Long time,Long cycleTimeSeconds){
        return this.sendSetTimeCycle(sender,files,time,TimeUnit.SECONDS,cycleTimeSeconds);
    }
    //获取附件文件的后缀名
    public String cutout(MultipartFile multipartFile) {
        String fileName=multipartFile.getOriginalFilename();
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        return prefix;
    }
    public boolean send(Sender sender, File...files){
        MimeMessage message =javaMailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(userProperties.getUsername()));
            message.setSentDate(new Date());
            message.setText(sender.getContent());
            //邮件收件人；TO表示主要接收人，CC表示抄送人，BCC表示秘密抄送人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sender.getTo()[0]));
            message.setSubject(sender.getSubject());
            if (files.length!=0){
                MimeBodyPart mimeBodyPart=new MimeBodyPart();
                MimeMultipart mimeMultipart=new MimeMultipart();
                mimeBodyPart.setContent(sender.getContent(), "text/html;charset=UTF-8");
                mimeMultipart.setSubType("related");
                //添加文本进入mimeMultipart
                mimeMultipart.addBodyPart(mimeBodyPart);
                for(File file:files) {
                    //将附件添加进入mimeMultipart
                    MimeBodyPart attachPart=new MimeBodyPart();
                    attachPart.attachFile(file);
                    mimeMultipart.addBodyPart(attachPart);
                }
                //将内容添加进邮件
                message.setContent(mimeMultipart);
                //保存邮件
                message.saveChanges();
            }else {
                message.setContent(sender.getContent(),"text/html;charset=UTF-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        javaMailSender.send(message);
        return true;
    }
    public boolean sendSetTime(Sender sender,File[]files,Date date){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,files);
            }
        };
        long now = new Date().getTime();
        long time = date.getTime();
        threadPoolExecutor.schedule(runnable,time-now,TimeUnit.MILLISECONDS);
        return true;
    }
    public boolean sendSetTime(Sender sender,File[]files,Long time,TimeUnit timeUnit){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,files);
            }
        };
        threadPoolExecutor.schedule(runnable,time,timeUnit);
        return true;
    }
    public boolean sendSetTime(Sender sender,File[]files,Long time){
        return this.sendSetTime(sender,files,time,TimeUnit.SECONDS);
    }
    public boolean sendSetTimeCycle(Sender sender,File[]files,Date date,Long cycleTimeSeconds){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,files);
            }
        };
        long now = new Date().getTime();
        long time = date.getTime();
        threadPoolExecutor.scheduleWithFixedDelay(runnable,time-now,cycleTimeSeconds,TimeUnit.MILLISECONDS);
        return true;
    }
    public boolean sendSetTimeCycle(Sender sender,File[]files,Long time,TimeUnit timeUnit,Long cycleTime){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,files);
            }
        };
        threadPoolExecutor.scheduleWithFixedDelay(runnable,time,cycleTime,timeUnit);
        return true;
    }
    public boolean sendSetTimeCycle(Sender sender,File[]files,Long time,Long cycleTimeSeconds){
        return this.sendSetTimeCycle(sender,files,time,TimeUnit.SECONDS,cycleTimeSeconds);
    }
    public boolean send(Sender sender, String filePath){
        MimeMessage message =javaMailSender.createMimeMessage();
        File file1 = new File(filePath);
        try {
            message.setFrom(new InternetAddress(userProperties.getUsername()));
            message.setSentDate(new Date());
            message.setText(sender.getContent());
            //邮件收件人；TO表示主要接收人，CC表示抄送人，BCC表示秘密抄送人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sender.getTo()[0]));
            message.setSubject(sender.getSubject());
            if (file1.isFile()){
                MimeBodyPart mimeBodyPart=new MimeBodyPart();
                MimeMultipart mimeMultipart=new MimeMultipart();
                mimeBodyPart.setContent(sender.getContent(), "text/html;charset=UTF-8");
                mimeMultipart.setSubType("related");
                //添加文本进入mimeMultipart
                mimeMultipart.addBodyPart(mimeBodyPart);
                //将附件添加进入mimeMultipart
                MimeBodyPart attachPart=new MimeBodyPart();
                attachPart.attachFile(file1);
                mimeMultipart.addBodyPart(attachPart);
                //将内容添加进邮件
                message.setContent(mimeMultipart);
                //保存邮件
                message.saveChanges();
            }else {
                message.setContent(sender.getContent(),"text/html;charset=UTF-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        javaMailSender.send(message);
        return true;
    }
    public boolean sendSetTime(Sender sender,String filePath,Date date){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,filePath);
            }
        };
        long now = new Date().getTime();
        long time = date.getTime();
        threadPoolExecutor.schedule(runnable,time-now,TimeUnit.MILLISECONDS);
        return true;
    }
    public boolean sendSetTime(Sender sender,String filePath,Long time,TimeUnit timeUnit){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,filePath);
            }
        };
        threadPoolExecutor.schedule(runnable,time,timeUnit);
        return true;
    }
    public boolean sendSetTime(Sender sender,String filePath,Long time){
        return this.sendSetTime(sender,filePath,time,TimeUnit.SECONDS);
    }
    public boolean sendSetTimeCycle(Sender sender,String filePath,Date date,Long cycleTimeSeconds){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,filePath);
            }
        };
        long now = new Date().getTime();
        long time = date.getTime();
        threadPoolExecutor.scheduleWithFixedDelay(runnable,time-now,cycleTimeSeconds,TimeUnit.MILLISECONDS);
        return true;
    }
    public boolean sendSetTimeCycle(Sender sender,String filePath,Long time,TimeUnit timeUnit,Long cycleTime){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                send(sender,filePath);
            }
        };
        threadPoolExecutor.scheduleWithFixedDelay(runnable,time,cycleTime,timeUnit);
        return true;
    }
    public boolean sendSetTimeCycle(Sender sender,String filePath,Long time,Long cycleTimeSeconds){
        return this.sendSetTimeCycle(sender,filePath,time,TimeUnit.SECONDS,cycleTimeSeconds);
    }
}
