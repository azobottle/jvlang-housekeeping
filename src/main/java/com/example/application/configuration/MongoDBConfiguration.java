package com.example.application.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@Slf4j
public class MongoDBConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String connectionString;
    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create(connectionString);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, databaseName);
    }
}
