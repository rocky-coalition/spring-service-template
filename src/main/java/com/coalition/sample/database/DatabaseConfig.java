package com.coalition.sample.database;

import com.coalition.core.configuration.secret.Secrets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource(@Autowired Secrets secrets){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://"+secrets.getSecrets().get("database.host")
                +":"+secrets.getSecrets().get("database.port")
                +"/"+secrets.getSecrets().get("database.name")  );
        dataSourceBuilder.username(secrets.getSecrets().get("database.username"));
        dataSourceBuilder.password(secrets.getSecrets().get("database.password"));
        return dataSourceBuilder.build();
    }

}
