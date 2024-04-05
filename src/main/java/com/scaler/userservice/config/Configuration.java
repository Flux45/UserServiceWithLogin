package com.scaler.userservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;


@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    @Bean
    public RestTemplate createRestTemplate() {
        return new RestTemplateBuilder().build();
    }
}
