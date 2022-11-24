package com.example.rabbitmq_exchange.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQHeaderConfig {
    @Bean
    Queue marketingQueueHeader() {
        return new Queue("marketingQueue", false);
    }

    @Bean
    Queue financeQueueHeader() {
        return new Queue("financeQueue", false);
    }

    @Bean
    Queue adminQueueHeader() {
        return new Queue("adminQueue", false);
    }

    @Bean
    HeadersExchange headerExchange() {
        return new HeadersExchange("header-exchange");
    }

    @Bean
    Binding marketingBindingHeader(Queue marketingQueue, HeadersExchange headerExchange) {
        return BindingBuilder.bind(marketingQueue).to(headerExchange).where("department").matches("marketing");
    }

    @Bean
    Binding financeBindingHeader(Queue financeQueue, HeadersExchange headerExchange) {
        return BindingBuilder.bind(financeQueue).to(headerExchange).where("department").matches("finance");
    }

    @Bean
    Binding adminBindingHeader(Queue adminQueue, HeadersExchange headerExchange) {
        return BindingBuilder.bind(adminQueue).to(headerExchange).where("department").matches("admin");
    }
}
