package com.codebrewers.onlinebookstore.configuration;

import com.codebrewers.onlinebookstore.properties.FileProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

@Autowired
FileProperties fileProperties;

@Bean
Queue createQueue() {
return new Queue("orderQueue", true, false, false);
}

@Bean
TopicExchange exchange() {
return new TopicExchange("orders");
}

@Bean
Binding binding(Queue queue, TopicExchange exchange) {
return BindingBuilder.bind(queue).to(exchange).with("junaid");
}

@Bean
public MessageConverter jsonMessageConverter() {
return new Jackson2JsonMessageConverter();
}
}