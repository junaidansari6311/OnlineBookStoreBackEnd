package com.codebrewers.onlinebookstore.rabbitmq.producer;

import com.codebrewers.onlinebookstore.dto.EmailDTO;
import com.codebrewers.onlinebookstore.properties.FileProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    FileProperties fileProperties;

    public void addToEmailQueue(EmailDTO emailDTO) {
        rabbitTemplate.convertAndSend("orders", "junaid", emailDTO);
    }
}