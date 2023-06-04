package com.alexaf.springkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${app.kafka.topic-1}")
    private String topic1;

    @Value("${app.kafka.topic-2}")
    private String topic2;

    @Value("${app.kafka.topic-3}")
    private String topic3;

    @Value("${app.kafka.topic-4}")
    private String topicUser;

    @Value("${app.kafka.topic-5}")
    private String topicOthers;

    @Value("${app.kafka.topic-6}")
    private String topicBytes;

    @Bean
    NewTopic topic1() {
        return TopicBuilder.name(topic1).build();
    }

    @Bean
    NewTopic topic2() {
        return TopicBuilder.name(topic2).build();
    }

    @Bean
    NewTopic topic3() {
        return TopicBuilder.name(topic3).build();
    }

    @Bean
    NewTopic topicUser() {
        return TopicBuilder.name(topicUser).build();
    }

    @Bean
    NewTopic topicOthers() {
        return TopicBuilder.name(topicOthers).build();
    }

    @Bean
    NewTopic topicBytes() {
        return TopicBuilder.name(topicBytes).build();
    }
}
