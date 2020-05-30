package com.codebrewers.onlinebookstore.utils.implementation;

import com.codebrewers.onlinebookstore.utils.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService implements IMailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendMail(String body,String subject,String emailID) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(emailID);
        helper.setSubject(subject);
        helper.setText(body,true);
        javaMailSender.send(message);
    }
}


