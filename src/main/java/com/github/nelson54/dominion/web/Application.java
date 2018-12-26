package com.github.nelson54.dominion.web;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.GameProvider;
import com.github.nelson54.dominion.KingdomFactory;
import com.github.nelson54.dominion.UsersProvider;
import com.github.nelson54.dominion.match.MatchProvider;
import com.github.nelson54.dominion.persistence.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;


@EnableAutoConfiguration
@ComponentScan({"com.github.nelson54.dominion.web"})
@EntityScan("com.github.nelson54.dominion.persistence.entities")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.github.nelson54.dominion.persistence")
//@EnableJpaRepositories
public class Application extends WebMvcConfigurerAdapter {

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
    UsersProvider getUsersProvider(){
        return new UsersProvider();
    }

    @Bean
    GameProvider getGameProvider() {
        return new GameProvider();
    }

    @Bean
    MatchProvider getMatchProvider() {
        return new MatchProvider();
    }

    @Autowired
    public ObjectMapper configureObjectMapper(ObjectMapper objectMapper) {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new GuavaModule());

        return objectMapper;
    }
}
