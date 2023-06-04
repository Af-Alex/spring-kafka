package com.alexaf.springkafka.init;

import com.alexaf.springkafka.User;
import com.alexaf.springkafka.sender.KafkaSenderExample;
import com.alexaf.springkafka.sender.KafkaSenderWithMessageConverter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class InitSend {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final KafkaSenderExample kafkaSenderExample;

    private final KafkaSenderWithMessageConverter messageConverterSender;

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


    @EventListener
    public void initiateSendingMessage(ApplicationReadyEvent event) throws InterruptedException {
        Thread.sleep(2500);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessage("I'll be received by MultipleTopicListener, Listener & ClassLevel KafkaHandler", topic1);

        Thread.sleep(2500);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessage("I'll be received by ListenToPartitionWithOffset", topic3);

        Thread.sleep(2500);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessageWithCallback("I'll get a async Callback", topicOthers);

        Thread.sleep(2500);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessage("I'll be ignored by RecordFilter", topic2);

        Thread.sleep(2500);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessageWithCallback("I'm sent using RoutingTemplate", topicBytes);

        Thread.sleep(2500);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessage("I will get reply back from @SendTo", topicOthers);

        Thread.sleep(2500);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendCustomMessage(new User("Alex"), topicUser);

        Thread.sleep(2500);
        LOG.info("---------------------------------");
        messageConverterSender.sendMessageWithConverter(new GenericMessage<>(new User("Stranger")));

        Thread.sleep(2500);
        LOG.warn("Application completed successfully, shutting down...");
        System.exit(1);
    }
}
