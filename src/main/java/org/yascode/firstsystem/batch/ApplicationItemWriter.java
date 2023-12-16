package org.yascode.firstsystem.batch;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.yascode.firstsystem.config.RabbitMQConfig;
import org.yascode.firstsystem.config.RabbitMQProducer;
import org.yascode.firstsystem.entity.Application;

@Component
public class ApplicationItemWriter implements ItemWriter<Application> {

    //private final AmqpTemplate myRabbitTemplate;
    //private final AmqpTemplate amqpTemplate;

    private final RabbitMQProducer rabbitMQProducer;

    public ApplicationItemWriter(RabbitMQProducer rabbitMQProducer) {
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    public void write(Chunk<? extends Application> chunk) {
        for (Application application : chunk) {
            rabbitMQProducer.sendMessage(application);
        }
    }
}
