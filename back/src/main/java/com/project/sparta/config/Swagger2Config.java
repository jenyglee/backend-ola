package com.project.sparta.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.service.contexts.SecurityContext;
@EnableSwagger2
@Configuration
public class Swagger2Config {
  // 스웨거 url : http://localhost:8080/swagger-ui.html

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        //.apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.project.sparta.hashtag.controller"))
        .paths(PathSelectors.any())
        .build();
  }
  //private ApiKey apiKey() {
  //  return new ApiKey("JWT", "jwt", "header");
  //}
  //
  //private SecurityContext securityContext() {
  //  return springfox
  //      .documentation
  //      .spi.service
  //      .contexts
  //      .SecurityContext
  //      .builder()
  //      .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
  //}
  //List<SecurityReference> defaultAuth() {
  //  AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
  //  AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
  //  authorizationScopes[0] = authorizationScope;
  //  return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  //}
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("잠은 죽어서 자자")
        .version("v1")
        .description("산에 대한 사이트 입니다.")
        .license("라이센스 작성")
        .licenseUrl("잠은 죽어서 자자 라이센스")
        .build();
  }
}