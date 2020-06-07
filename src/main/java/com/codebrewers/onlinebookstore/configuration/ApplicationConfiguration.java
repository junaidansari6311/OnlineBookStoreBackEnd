package com.codebrewers.onlinebookstore.configuration;

import com.codebrewers.onlinebookstore.utils.implementation.MailService;
import com.codebrewers.onlinebookstore.utils.implementation.Token;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class ApplicationConfiguration {

    @Bean
    public JavaMailSender javaMailSender(){
        return new JavaMailSenderImpl();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Token token(){
        return new Token();
    }

}
