package com.job.lk.webapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173/")
                .allowedMethods(
                        "GET",
                        "POST",
                        "OPTIONS",
                        "PUT",
                        "DELETE",
                        "PATCH")
                .allowedHeaders(
                        "Content-Type", "Authorization")
                .allowCredentials(
                        true
                );
    }
}
