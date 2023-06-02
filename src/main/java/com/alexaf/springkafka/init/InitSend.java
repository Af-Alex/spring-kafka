package com.alexaf.springkafka.init;

import com.alexaf.springkafka.User;
import com.alexaf.springkafka.sender.KafkaSenderExample;
import com.alexaf.springkafka.sender.KafkaSenderWithMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
class InitSend {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private KafkaSenderExample kafkaSenderExample;

    @Autowired
    private KafkaSenderWithMessageConverter messageConverterSender;

    @Value("${app.kafka.topic-1}")
    private String topic1;

    @Value("${app.kafka.topic-2}")
    private String topic2;

    @Value("${app.kafka.topic-3}")
    private String topic3;

    @EventListener
    public void initiateSendingMessage(ApplicationReadyEvent event) throws InterruptedException {
        Thread.sleep(5000);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessage("I'll be received by MultipleTopicListener, Listener & ClassLevel KafkaHandler", topic1);

        Thread.sleep(5000);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessage("I'll be received by ListenToPartitionWithOffset", topic3);

        Thread.sleep(5000);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessageWithCallback("I'll get a asyc Callback", "reflectoring-others");

        Thread.sleep(5000);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessageWithCallback("I'm sent using RoutingTemplate", "reflectoring-bytes");

        Thread.sleep(5000);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessage("I'll be ignored by RecordFilter", topic3);

        Thread.sleep(5000);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendMessage("I will get reply back from @SendTo", "reflectoring-others");


        Thread.sleep(5000);
        LOG.info("---------------------------------");
        kafkaSenderExample.sendCustomMessage(new User("Alex"), "topic-user");

        Thread.sleep(5000);
        LOG.info("---------------------------------");
        messageConverterSender.sendMessageWithConverter(new GenericMessage<>(new User("Stranger")));
    }
}
