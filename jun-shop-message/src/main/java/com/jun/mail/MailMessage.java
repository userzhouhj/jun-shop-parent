package com.jun.mail;

import com.alibaba.fastjson.JSONObject;
import com.jun.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 负责邮件发送
 */
@Slf4j
@Component
public class MailMessage implements MessageAdapter {
    @Value("${msg.subject}")
    private String subject;
    @Value("${msg.text}")
    private String text;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMsg(JSONObject body) {
        String email = body.getString("email");
        if(email.isEmpty()){
            return ;
        }
        log.info("#########正在向 "+email+"发送邮件#########");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(email);//邮件来自
        simpleMailMessage.setTo(email);//邮件发送到

        simpleMailMessage.setSubject(subject);//设置主题
        simpleMailMessage.setText(text.replace("{}",email));//设置邮件内容

        javaMailSender.send(simpleMailMessage);//邮件发送


        log.info("############邮件发送成功##########");
    }
}
