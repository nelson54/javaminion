package com.github.nelson54.dominion.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.github.nelson54.dominion.GameFactory;
import com.github.nelson54.dominion.GameProvider;
import com.github.nelson54.dominion.KingdomFactory;
import com.github.nelson54.dominion.web.users.SecurityContext;
import com.github.nelson54.dominion.web.users.SimpleSignInAdapter;
import com.github.nelson54.dominion.web.users.User;
import org.apache.catalina.realm.JNDIRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.social.config.support.InMemoryConnectionRepositoryConfigSupport;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.inject.Inject;


@EnableAutoConfiguration
@ComponentScan("com.github.nelson54.dominion.web")
@PropertySource("classpath:application.properties")
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
}
