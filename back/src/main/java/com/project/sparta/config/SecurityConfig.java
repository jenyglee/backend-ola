package com.project.sparta.config;


import com.project.sparta.security.JwtAccessDeniedHandler;
import com.project.sparta.security.JwtAuthenticationEntryPoint;
import com.project.sparta.security.jwt.JwtAuthFilter;
import com.project.sparta.security.jwt.JwtUtil;
import com.project.sparta.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers(HttpMethod.POST, "/auth/signup")       //회원가입 api 필터제외 -> api 나오면 수정 요함
                .antMatchers(HttpMethod.POST, "/auth/signup/admin")       //회원가입 api 필터제외 -> api 나오면 수정 요함
                .antMatchers(HttpMethod.POST, "/auth/login")      //로그인 api 필터제외 -> api 나오면 수정 요함
                .antMatchers(HttpMethod.POST, "/auth/logout")
                .antMatchers(HttpMethod.POST, "/auth/regenerateToken");
//                .antMatchers(HttpMethod.POST,"/api/board");

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{

        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);        //세션을 만들지도 않고, 기존것을 사용하지 않겠다. -> JWT 토큰을 사용할 떄 주로 사용함.

        // exception handling for jwt
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        httpSecurity.authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/boards/**").authenticated()
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/friends/**").authenticated()
                .anyRequest().permitAll()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil, redisTemplate), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.formLogin().disable();

        return httpSecurity.build();
    }


}
