package com.github.nelson54.dominion.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.GameProvider;
import com.github.nelson54.dominion.KingdomFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.ShallowEtagHeaderFilter;


@EnableAutoConfiguration
@ComponentScan("com.github.nelson54.dominion.web")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    GameFactory getGameFactory() {
        GameFactory gameFactory = new GameFactory();
        KingdomFactory kingdomFactory = new KingdomFactory();
        gameFactory.setKingdomFactory(kingdomFactory);
        return gameFactory;
    }

    @Bean
    GameProvider getGameProvider() {
        return new GameProvider();
    }

    @Bean
    public ObjectMapper getObjectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new GuavaModule());

        return objectMapper;
    }

    @Bean
    public ShallowEtagHeaderFilter getEtagFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
