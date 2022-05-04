package com.technophiles.diaryapp;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableAutoConfiguration
@Configuration
@EnableMongoRepositories(basePackages = {"com.technophiles.diaryapp.repositories"})
public class DataConfig {
}
