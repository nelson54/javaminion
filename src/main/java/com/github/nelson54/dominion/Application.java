package com.github.nelson54.dominion;

import com.github.cloudyrock.spring.v5.EnableMongock;
import com.github.nelson54.dominion.game.AiSimulator;
import com.github.nelson54.dominion.game.GameFactory;
import com.github.nelson54.dominion.game.KingdomFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableMongock
@SpringBootApplication
@ConfigurationPropertiesScan(value = {
        "com.github.nelson54.dominion.match"
})

@ComponentScan(value = {
        "com.github.nelson54.dominion.view",
        "com.github.nelson54.dominion.game",
        "com.github.nelson54.dominion.game.commands",
        "com.github.nelson54.dominion.game.ai",
        "com.github.nelson54.dominion.match",
        "com.github.nelson54.dominion.spring",
        "com.github.nelson54.dominion.spring.security",
        "com.github.nelson54.dominion.user",
        "com.github.nelson54.dominion.user.account",
        "com.github.nelson54.dominion.user.authorization",
        "com.github.nelson54.dominion.cards",
    }, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value= AiSimulator.class)
    })
@EnableMongoAuditing
@EnableMongoRepositories({
        "com.github.nelson54.dominion.user",
        "com.github.nelson54.dominion.user.account",
        "com.github.nelson54.dominion.match",
        "com.github.nelson54.dominion.game.commands",
        "com.github.nelson54.dominion.user.authorization",
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    GameFactory getGameFactory(KingdomFactory kingdomFactory) {
        return new GameFactory(kingdomFactory);

    }

    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
