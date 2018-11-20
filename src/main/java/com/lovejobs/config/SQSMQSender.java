package com.lovejobs.config;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Component
public class SQSMQSender{

    @Autowired
    private AmazonSQS amazonSQS;

    public void send(String message) throws Exception {
        amazonSQS.sendMessage(new SendMessageRequest("https://sqs.ap-south-1.amazonaws.com/848318613114/test-queue-dev", message));
    }
}
