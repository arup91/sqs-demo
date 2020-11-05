package com.aws.sqsdemo;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.aws.sqsdemo.queue.CreateQueue;
import com.aws.sqsdemo.queue.DeleteQueue;
import com.aws.sqsdemo.queue.PublishMessages;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {ContextInstanceDataAutoConfiguration.class})
@EnableSqs
public class SqsDemoApplication {

	@Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsyncClient) {
        return new QueueMessagingTemplate(amazonSQSAsyncClient);
    }

	public static void main(String[] args) throws JsonProcessingException {
		ApplicationContext context = SpringApplication.run(SqsDemoApplication.class, args);
		createQueues(context);
		publishMessages(context);
		deleteQueues(context);
	}

	private static void createQueues(ApplicationContext context) {
		CreateQueue createQueue = context.getBean(CreateQueue.class);
		createQueue.createStandardQueue("SQS_DEMO_QUEUE");
		createQueue.createFifoQueue("SQS_DEMO_QUEUE.fifo");
	}

	private static void publishMessages(ApplicationContext context) throws JsonProcessingException {
		PublishMessages publishMessages = context.getBean(PublishMessages.class);
		publishMessages.publishMessageToStandardQueue("Hello for Standard Queue");
		publishMessages.publishMessageToFifoQueue("Hello For Fifo Queue");
	}

	private static void deleteQueues(ApplicationContext context) {
		DeleteQueue deleteQueue = context.getBean(DeleteQueue.class);
		deleteQueue.deleteQueue("SQS_DEMO_QUEUE");
		deleteQueue.deleteQueue("SQS_DEMO_QUEUE.fifo");
	}
}
