package com.github.nelson54.dominion.web;

import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.KingdomFactory;
import com.github.nelson54.dominion.services.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

@ComponentScan({
        "com.github.nelson54.dominion",
        "com.github.nelson54.dominion.web",
        "com.github.nelson54.dominion.services",
        "com.github.nelson54.dominion.ai"
})

@EntityScan("com.github.nelson54.dominion.persistence.entities")

@EnableJpaRepositories("com.github.nelson54.dominion.persistence")
@EnableMongoRepositories("com.github.nelson54.dominion.commands")

@Configuration
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }

    @Bean
    GameFactory getGameFactory(KingdomFactory kingdomFactory, CommandService commandService) {
        return new GameFactory(kingdomFactory, commandService);

    }

    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    void clearMongo(MongoTemplate mongoTemplate) {
        mongoTemplate.dropCollection("commands");
    }

}
