package com.DWmarket.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public  void sendMail(String toEmail, String link,String client){
        String host = "smtp.naver.com";
        String user = "rlatlgh9388@naver.com";
        String password = "k1b2s3d44!";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(user,password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(user));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            message.setSubject("비밀번호 초기화 메일입니다.");
            //  message.setHeader("APPLICATION content/type HTML ","");
            message.setText("비밀번호가 초기화 되어 새로운 비밀번호를 변경 부탁드립니다. " +
                    "<a href='"+link+"'>"+link+"</a>"  ,"utf-8","html");
//            "<a href='"+link+"/"+client+"'>"+link+"/"+client+"</a>","utf-8","html");


            Transport.send(message);

            return;
        }catch (MessagingException e){
            e.printStackTrace();
            return;
        }
    }
}
