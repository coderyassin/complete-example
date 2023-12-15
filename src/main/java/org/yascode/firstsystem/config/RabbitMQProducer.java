package org.yascode.firstsystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.yascode.firstsystem.entity.Application;

@Service
public class RabbitMQProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    private static final String EXCHANGE = "yascode_exchange";
    private static final String ROUTING_KEY = "yascode_routing_key";

    private final AmqpTemplate amqpTemplate;

    public RabbitMQProducer(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessage(Application message){
        LOGGER.info(String.format("Message sent -> %s", message));
        amqpTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
    }

}
