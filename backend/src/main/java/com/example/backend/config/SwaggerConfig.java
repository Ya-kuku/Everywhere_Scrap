package com.example.backend.config;

import com.google.common.base.Predicates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 하나 이상의 bean 요청들을 처리 
@Configuration
// swagger 사용하게 해줌
@EnableSwagger2
public class SwaggerConfig {
    // Bean은 메서드라 생각하면 됨
    @Bean
    // Docket이라는 bean을 정의
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any()).build();
    }
}

