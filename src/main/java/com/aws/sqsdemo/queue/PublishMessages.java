package com.aws.sqsdemo.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PublishMessages {

    private final QueueMessagingTemplate queueMessagingTemplate;

    public PublishMessages(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    public void publishMessageToStandardQueue(String message) {
        queueMessagingTemplate.send("SQS_DEMO_QUEUE", MessageBuilder.withPayload(message).build());
    }

    public void publishMessageToFifoQueue(String message) {
        Map<String, Object> headers = new HashMap<>();
        // Message Group ID being set
        headers.put(SqsMessageHeaders.SQS_GROUP_ID_HEADER, "1");
        // Below is optional, since Content based de-duplication is enabled
        headers.put(SqsMessageHeaders.SQS_DEDUPLICATION_ID_HEADER, "2");
        queueMessagingTemplate.send("SQS_DEMO_QUEUE.fifo", MessageBuilder.withPayload(message).copyHeaders(headers).build());
    }
}


