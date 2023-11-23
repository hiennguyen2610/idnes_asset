package com.example.asset_management_idnes.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)

public class EmailService {

    @Autowired
    private  JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private  String sender;



    @Async
    public void sendEmailToRecipients(String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(sender);
        helper.setTo("quanghien485@gmail.com");
        helper.addTo("kimlamvu1z@gmail.com");
        helper.setSubject(subject);
        helper.setText(text);
        javaMailSender.send(message);
    }
}
