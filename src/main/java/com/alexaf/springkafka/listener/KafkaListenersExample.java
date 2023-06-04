package com.alexaf.springkafka.listener;

import com.alexaf.springkafka.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
class KafkaListenersExample {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = "newTopic1")
    void listener(String message) {
        LOG.info("Listener [{}]", message);
    }

    @KafkaListener(topics = { "newTopic1", "newTopic2" }, groupId = "topic-group-2")
    void commonListenerForMultipleTopics(String message) {
        LOG.info("MultipleTopicListener - [{}]", message);
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "newTopic3", partitionOffsets = {
            @PartitionOffset(partition = "0", initialOffset = "0") }), groupId = "topic-group-3")
    void listenToPartitionWithOffset(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                     @Header(KafkaHeaders.OFFSET) int offset) {
        LOG.info("ListenToPartitionWithOffset [{}] from partition-{} with offset-{}", message, partition, offset);
    }

    @KafkaListener(id = "1", topics = "newTopic-user",
            groupId = "topic-user-mc", containerFactory = "kafkaJsonListenerContainerFactory")
    void listenerWithMessageConverter(User user) {
        LOG.info("MessageConverterUserListener [{}]", user);
    }

    @KafkaListener(topics = "newTop-bytes")
    void listenerForRoutingTemplate(String message) {
        LOG.info("RoutingTemplate BytesListener [{}]", message);
    }

    @KafkaListener(topics = "newTopic-others")
    @SendTo("newTopic2")
    String listenAndReply(String message) {
        LOG.info("ListenAndReply [{}]", message);
        return "This is a reply sent to 'newTopic2' topic after receiving message at 'newTopic-others' topic";
    }
}
