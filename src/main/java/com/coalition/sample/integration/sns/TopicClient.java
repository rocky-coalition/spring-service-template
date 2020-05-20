package com.coalition.sample.integration.sns;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.util.EC2MetadataUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TopicClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicClient.class);
    private final AmazonSNS amazonSNS;
    private static final EC2MetadataUtils.InstanceInfo INFO = EC2MetadataUtils.getInstanceInfo();

    @Value("${sns.topics}")
    private String[] topics;

    @Autowired
    public TopicClient(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public void publish(String message) {
        for(String topic : topics){
            PublishResult result = this.amazonSNS.publish(getTopicArn(topic), message);
            if(StringUtils.isBlank(result.getMessageId())) {
                LOGGER.error("message [{}] publish failed to topic [{}]", message, getTopicArn(topic));
            } else{
                LOGGER.info("message [{}] publish succeeded to topic [{}] with id [{}]", message, getTopicArn(topic), result.getMessageId());
            }
        }

    }

    private String getTopicArn(String topicName) {
        if(INFO != null) {
            return "arn:aws:sns:" + INFO.getRegion() + ":" + INFO.getAccountId() + ":" + topicName;
        } else {
            return "arn:aws:sns:us-east-1:000000000000:"+topicName;
        }
    }
}
