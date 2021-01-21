package com.example.backend.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class MongoApplication {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            Account account = new Account();
            account.setEmail("ektha@naver.com");
            account.setUsername("som");

            mongoTemplate.insert(account);
        };
    }
}
