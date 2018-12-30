package com.github.nelson54.dominion.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().anyRequest().permitAll();

        http
                .headers()
                .cacheControl()
                .disable();

        http
                .csrf().disable()
                .authorizeRequests()
                //.antMatchers("/").hasAnyRole("USER")
                .antMatchers("/test").hasAnyRole("USER")
                .antMatchers("/user").hasAnyRole("USER")
                .anyRequest().permitAll()
                .and()
                .logout()
                .permitAll();

        http
                .headers()
                .cacheControl()
                .disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }*/

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}