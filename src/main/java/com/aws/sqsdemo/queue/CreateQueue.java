package com.aws.sqsdemo.queue;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateQueue {

    AmazonSQSAsync amazonSQSAsyncClient;

    public CreateQueue(AmazonSQSAsync amazonSQSAsyncClient) {
        this.amazonSQSAsyncClient = amazonSQSAsyncClient;
    }

    public String createStandardQueue(String standardQueueName) {
        try {
            CreateQueueRequest standardQueueRequest = new CreateQueueRequest()
                    .withQueueName(standardQueueName)
                    .addAttributesEntry("VisibilityTimeout", "60")
                    .addAttributesEntry("DelaySeconds", "10")
                    .addAttributesEntry("MessageRetentionPeriod", "86400");

            return amazonSQSAsyncClient.createQueue(standardQueueRequest).getQueueUrl();

        } catch (AmazonSQSException exception) {
            if (!exception.getErrorCode().equals("QueueAlreadyExists")) {
                throw exception;
            }
        }
        return null;
    }

    public String createFifoQueue(String fifoQueueName) {
        try {
            CreateQueueRequest fifoQueueRequest = new CreateQueueRequest()
                    .withQueueName(fifoQueueName)
                    .addAttributesEntry("FifoQueue", "true")
                    .addAttributesEntry("VisibilityTimeout", "60")
                    .addAttributesEntry("DelaySeconds", "10")
                    .addAttributesEntry("ContentBasedDeduplication", "true")
                    .addAttributesEntry("MessageRetentionPeriod", "86400");

            return amazonSQSAsyncClient.createQueue(fifoQueueRequest).getQueueUrl();
        } catch (AmazonSQSException exception) {
            if (!exception.getErrorCode().equals("QueueAlreadyExists")) {
                throw exception;
            }
        }
        return null;
    }
}
