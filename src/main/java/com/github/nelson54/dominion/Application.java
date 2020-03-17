package com.github.nelson54.dominion;

import com.github.nelson54.dominion.game.AiSimulator;
import com.github.nelson54.dominion.game.GameFactory;
import com.github.nelson54.dominion.game.KingdomFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

@ComponentScan(value = {
        "com.github.nelson54.dominion.game",
        "com.github.nelson54.dominion.game.commands",
        "com.github.nelson54.dominion.game.ai",
        "com.github.nelson54.dominion.match",
        "com.github.nelson54.dominion.spring",
        "com.github.nelson54.dominion.spring.security",
        "com.github.nelson54.dominion.user",
        "com.github.nelson54.dominion.user.account",
        "com.github.nelson54.dominion.user.authorization",
    }, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value= AiSimulator.class)
    })

@EntityScan({
        "com.github.nelson54.dominion.user",
        "com.github.nelson54.dominion.user.account",
        "com.github.nelson54.dominion.match",
})

@EnableJpaRepositories({
        "com.github.nelson54.dominion.user",
        "com.github.nelson54.dominion.user.account",
        "com.github.nelson54.dominion.match",
})
@EnableMongoRepositories("com.github.nelson54.dominion.game.commands")
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.SERVLET);
        app.run(args);
    }

    @Bean
    GameFactory getGameFactory(KingdomFactory kingdomFactory) {
        return new GameFactory(kingdomFactory);

    }

    @Bean
    public BCryptPasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
