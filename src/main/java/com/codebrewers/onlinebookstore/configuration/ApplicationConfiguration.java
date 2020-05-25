package com.codebrewers.onlinebookstore.configuration;

import com.codebrewers.onlinebookstore.service.implementation.MailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public MailService mailService(){
        return new MailService();
    }

    @Bean
    public JavaMailSender javaMailSender(){
        return new JavaMailSenderImpl();
    }

}
