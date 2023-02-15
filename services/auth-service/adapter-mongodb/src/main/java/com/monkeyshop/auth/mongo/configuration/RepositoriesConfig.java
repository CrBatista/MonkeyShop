package com.monkeyshop.auth.mongo.configuration;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.monkeyshop.auth.mongo.repositories")
public class RepositoriesConfig {
}
