package com.codebrewers.onlinebookstore.rabbitmq.consumer;

import com.codebrewers.onlinebookstore.dto.EmailDTO;
import com.codebrewers.onlinebookstore.utils.IMailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class EmailHandler {

@Autowired
IMailService mailService;

@RabbitListener(queues ="orderQueue")
public void handleEmailQueue(EmailDTO emailDTO) throws IOException, MessagingException {
    mailService.sendMail(emailDTO.message,emailDTO.subject,emailDTO.email);
    System.out.println("Email Sent Successfully To " + emailDTO.email);
    }
}