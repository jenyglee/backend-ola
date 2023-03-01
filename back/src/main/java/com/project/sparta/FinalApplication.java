package com.project.sparta;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.persistence.EntityManager;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
@EnableCaching
public class FinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalApplication.class, args);
    }
    @Bean
    Hibernate5Module hibernate5Module(){
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        // hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
        return hibernate5Module;
    }

//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
}
