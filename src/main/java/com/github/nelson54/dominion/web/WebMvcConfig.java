package com.github.nelson54.dominion.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    Environment environment;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if((Arrays.asList(environment.getActiveProfiles()).contains("prod"))) {
            registry.addResourceHandler("/public/**")
                    .addResourceLocations("file:/root/builds/frontend/current/dist/");

            registry.addResourceHandler("/**")
                    .addResourceLocations("file:/root/builds/frontend/current/dist/")
                    .setCacheControl(CacheControl.maxAge(10, TimeUnit.DAYS))
                    .resourceChain(false);
        } else {
            registry.addResourceHandler("/public/**")
                    .addResourceLocations(
                            "file:/Users/dcnelson/projects/dominion-frontend/dist/",
                            "file:/Users/derek/IdeaProjects/dominion-frontend/dist/"
                    )
                    .resourceChain(false);

            registry.addResourceHandler("/**")
                    .addResourceLocations(
                            "file:/Users/dcnelson/projects/dominion-frontend/dist/",
                            "file:/Users/derek/IdeaProjects/dominion-frontend/dist/"
                    ).setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS))
                    .resourceChain(false);
        }

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.findAndRegisterModules();

        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        converters.add(new StringHttpMessageConverter());
    }

    @Bean
    public StringHttpMessageConverter stringMessageConverter() {
        return new StringHttpMessageConverter();
    }

}