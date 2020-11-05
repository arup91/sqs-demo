package com.aws.sqsdemo.queue;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import org.springframework.stereotype.Component;

@Component
public class DeleteQueue {

    AmazonSQSAsync amazonSQSAsyncClient;

    public DeleteQueue(AmazonSQSAsync amazonSQSAsyncClient) {
        this.amazonSQSAsyncClient = amazonSQSAsyncClient;
    }

    public void deleteQueue(String queueName) {
        DeleteQueueRequest deleteQueueRequest = new DeleteQueueRequest()
                .withQueueUrl(amazonSQSAsyncClient.getQueueUrl(queueName).getQueueUrl());
        amazonSQSAsyncClient.deleteQueue(deleteQueueRequest);
    }

}
