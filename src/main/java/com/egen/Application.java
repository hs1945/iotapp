package com.egen;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.easyrules.api.RulesEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;


/**
 * Created by Harjinder Singh on 5/1/2016.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.egen.spring"})
public class Application {
    public static final MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(com.egen.Application.class, args);



        return;

    }

}
