package com.example.rabbitmq_exchange.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicConfig {
    @Bean
    Queue marketingQueueTopic() {
        return new Queue("marketingQueue", false);
    }

    @Bean
    Queue financeQueueTopic() {
        return new Queue("financeQueue", false);
    }

    @Bean
    Queue adminQueueTopic() {
        return new Queue("adminQueue", false);
    }

    @Bean
    Queue allQueue() {
        return new Queue("allQueue", false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }

    @Bean
    Binding marketingBindingTopic(Queue marketingQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(marketingQueue).to(topicExchange).with("queue.marketing");
    }

    @Bean
    Binding financeBindingTopic(Queue financeQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(financeQueue).to(topicExchange).with("queue.finance");
    }

    @Bean
    Binding adminBindingTopic(Queue adminQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(adminQueue).to(topicExchange).with("queue.admin");
    }

    @Bean
    Binding allBinding(Queue allQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(allQueue).to(topicExchange).with("queue.*");
    }
}
