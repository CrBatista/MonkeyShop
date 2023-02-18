package com.monkeyshop.customer.mongo.configuration;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.monkeyshop.customer.mongo.repositories")
public class RepositoriesConfig {
}
