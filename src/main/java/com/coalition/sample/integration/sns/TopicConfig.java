package com.coalition.sample.integration.sns;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public AmazonSNS amazonSNS() {
        if(StringUtils.equalsIgnoreCase(profile, "local") ){
            return AmazonSNSClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("x", "x")))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4575", "us-west-2"))
                    .build();

        } else if(StringUtils.equalsIgnoreCase(profile, "local-docker")) {
            return AmazonSNSClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("x", "x")))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localstack:4575", "us-west-2"))
                    .build();
        } else {
            return AmazonSNSClientBuilder.standard().build();
        }
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}

