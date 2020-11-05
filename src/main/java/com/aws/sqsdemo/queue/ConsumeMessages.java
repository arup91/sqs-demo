package com.aws.sqsdemo.queue;

import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConsumeMessages {

    @SqsListener(value = "SQS_DEMO_QUEUE", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void listenToStandardQueue(String message, Acknowledgment acknowledgment, @Headers Map<String, String> headers) throws Exception {
            System.out.println(message);
            acknowledgment.acknowledge().get();
    }

    @SqsListener(value = "SQS_DEMO_QUEUE.fifo", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void listenToFIFOQueue(String message, Acknowledgment acknowledgment, @Headers Map<String, String> headers) throws Exception {
        System.out.println(message);
        acknowledgment.acknowledge().get();
    }
}
