package com.lovejobs.config;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnClass({com.amazonaws.services.sqs.AmazonSQSClientBuilder.class, com.amazonaws.services.sqs.AmazonSQS.class})
public class AmazonSQSConfig {

    @Bean
    public AmazonSQS amzonSQS(){
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        return sqs;
    }
}

