package com.project.sparta.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.spi.service.contexts.SecurityContext;

//http://localhost:8080/swagger-ui.html
@EnableSwagger2
@Configuration
public class Swagger2Config extends WebMvcConfigurerAdapter {


  @Bean
  public Docket boardApi() {
    return getDocket("게시글", Predicates.or(
        PathSelectors.regex("/boards.*")));
  }

  @Bean
  public Docket commentApi() {
    return getDocket("댓글", Predicates.or(
        PathSelectors.regex("/comments.*")));
  }

  @Bean
  public Docket hashtagApi() {
    return getDocket("해시태그", Predicates.or(
        PathSelectors.regex("/hashtags.*")));
  }

  @Bean
  public Docket likeApi() {
    return getDocket("좋아요", Predicates.or(
        PathSelectors.regex("/like.*")));
  }

  @Bean
  public Docket alarmApi() {
    return getDocket("알람", Predicates.or(
        PathSelectors.regex("/alarm.*")));
  }

  @Bean
  public Docket chatApi() {
    return getDocket("채팅", Predicates.or(
        PathSelectors.regex("/chat.*")));
  }

  @Bean
  public Docket friendApi() {
    return getDocket("친구", Predicates.or(
        PathSelectors.regex("/friends.*")));
  }

  @Bean
  public Docket productApi() {
    return getDocket("상품", Predicates.or(
        PathSelectors.regex("/product.*")));
  }

  @Bean
  public Docket infoApi() {
    return getDocket("정보", Predicates.or(
        PathSelectors.regex("/info.*")));
  }

  @Bean
  public Docket authApi() {
    return getDocket("회원", Predicates.or(
        PathSelectors.regex("/auth.*")));
  }


  @Bean
  public Docket adminApi() {
    return getDocket("어드민", Predicates.or(
        PathSelectors.regex("/admin.*")));
  }

  //swagger 설정.
  public Docket getDocket(String groupName, Predicate<String> predicate) {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName(groupName)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.project.sparta"))
        .paths(predicate)
        .build()
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()));
  }

  // 인증하는 방식
  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
  }

  // Authorization 버튼을 클릭했을 때 입력하는 값
  private ApiKey apiKey() {
    return new ApiKey("Authorization", "Authorization", "header");
  }

  //swagger ui 설정.
  @Bean
  public UiConfiguration uiConfig() {
    return UiConfigurationBuilder.builder()
        .displayRequestDuration(true)
        .validatorUrl("")
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
  //private ApiInfo apiInfo() {
  //  return new ApiInfoBuilder()
  //      .title("잠은 죽어서 자자")
  //      .version("v1")
  //      .description("산에 대한 사이트 입니다.")
  //      .license("라이센스 작성")
  //      .licenseUrl("잠은 죽어서 자자 라이센스")
  //      .build();
  //}
}