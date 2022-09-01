package com.barrista.jdm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender
{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String emailTo, String subject, String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    // TODO
    public void sendHTML(String emailTo, String subject, String messageHTML)
    {

//        MimeMessage message = new MimeMessage();
//        try
//        {
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo, false));
//            message.setFrom(new InternetAddress(username, false));
//            //message.setFrom(username);
//            //message.setTo(emailTo);
//            message.setSubject(subject);
//            message.setContent(messageHTML, "text/html; charset=utf-8");
//
//            //message.setSentDate(new Date());
//            mailSender.send(message);
//        }
//        catch(Exception ex)
//        {
//            System.out.println("ERROR LOG = " + ex);
//        }
    }
}
