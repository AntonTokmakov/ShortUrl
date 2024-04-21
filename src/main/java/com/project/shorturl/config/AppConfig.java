package com.project.shorturl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public String baseUrl() {
        return "http://localhost:8090/api/v1";
    }
}
