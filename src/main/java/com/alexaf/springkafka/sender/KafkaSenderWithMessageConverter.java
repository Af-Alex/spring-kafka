package com.alexaf.springkafka.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class KafkaSenderWithMessageConverter {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private KafkaTemplate<String, ?> kafkaTemplate;

    public void sendMessageWithConverter(Message<?> user) {
        LOG.info("Sending With Message Converter : {}", user);
        LOG.info("--------------------------------");

        kafkaTemplate.send(user);
    }

}
