package com.coalition.core.configuration.secret;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Getter
@EnableScheduling
public class SecretsConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecretsConfig.class);

    @Value("${aws.secretslist}")
    private String[] secretsList;

    @Value("${aws.secretsmanager.name}")
    private String smName;

    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    @Autowired
    public Secrets secrets (AWSSecretsManager smClient) {
        Secrets secrets = null;
        try  {
            GetSecretValueRequest  getSecretValueRequest  =  new GetSecretValueRequest().withSecretId(smName);
            GetSecretValueResult getSecretValueResponse = smClient.getSecretValue(getSecretValueRequest);
            if  (getSecretValueResponse  !=  null)  {
                if(secrets == null)  secrets = Secrets.builder().build();
                ObjectMapper mapper = new ObjectMapper();
                TypeReference<HashMap<String, String>> typeRef  = new TypeReference<>() {};
                Map<String, String> map = mapper.readValue(getSecretValueResponse.getSecretString(), typeRef);
                for (String s : secretsList) {
                    secrets.getSecrets().put(s, map.get(s));
                }
            }
        } catch  (InvalidRequestException e)  {
            LOGGER.error("The request was invalid due to: "  +  e.getMessage());
        } catch  (InvalidParameterException e)  {
            LOGGER.error("The request had invalid params: "  +  e.getMessage());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ResourceNotFoundException e) {
            LOGGER.error("The requested secret was not found for path {}", smName);
        }

        return secrets;
    }

    @Bean
    public AWSSecretsManager smClient () {
        if(StringUtils.equalsIgnoreCase(profile, "local")){
            AwsClientBuilder.EndpointConfiguration  config  =  new  AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1");
            AWSSecretsManagerClientBuilder clientBuilder  =  AWSSecretsManagerClientBuilder.standard();
            clientBuilder.setEndpointConfiguration(config);
            return clientBuilder.build();
        }else if(StringUtils.equalsIgnoreCase(profile, "local-docker")){
            AwsClientBuilder.EndpointConfiguration  config  =  new  AwsClientBuilder.EndpointConfiguration("http://localstack:4566", "us-east-1");
            AWSSecretsManagerClientBuilder clientBuilder  =  AWSSecretsManagerClientBuilder.standard();
            clientBuilder.setEndpointConfiguration(config);
            return clientBuilder.build();
        }else {
            return  AWSSecretsManagerClientBuilder.defaultClient();
        }
    }
}
