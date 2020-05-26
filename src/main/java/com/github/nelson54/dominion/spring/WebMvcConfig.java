package com.github.nelson54.dominion.spring;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    Environment environment;

    @Autowired
    ObjectMapper objectMapper;

    @Controller
    static class Routes {
        @RequestMapping("/{path:[^\\.]+}/**")
        public String forward() {
            return "forward:/index.html";
        }
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS))
                .addResourceLocations("classpath:/public");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/public/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.SECONDS))
                .resourceChain(false);

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.findAndRegisterModules();

        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

}