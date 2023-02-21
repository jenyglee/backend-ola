package com.project.sparta.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

     @Override
     public void addCorsMappings(CorsRegistry registry) {

         registry.addMapping("/**")      //패턴
             .allowedOrigins("*")    //URL
             .allowedOrigins("http://localhost:8080","http://localhost:63342") //URL
             .allowedHeaders("Authorization", "Content-type")
             .exposedHeaders("Authorization", "Content-Disposition")
             .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");        //method
     }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/2.6.1/");

    }
}

