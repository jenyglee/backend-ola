package com.project.sparta.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sparta.exception.CustomException;
import com.project.sparta.security.dto.SecurityExceptionDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.project.sparta.security.jwt.JwtUtil.AUTHORIZATION_HEADER;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    public RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);
        String resultToekn = token.split(" ")[1].trim();

        try {
            if (token != null) {
                if (!jwtUtil.validateToken(resultToekn)) {
                    jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                    return;
                }
                // Redis에 해당 accessToken logout 여부를 확인
                String isLogout = (String) redisTemplate.opsForValue().get(resultToekn);

                // 로그아웃이 없는(되어 있지 않은) 경우 해당 토큰은 정상적으로 작동하기
                if (ObjectUtils.isEmpty(isLogout)) {

                    Authentication auth = jwtUtil.getAuthenticationByAccessToken(token);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (CustomException e) {
            SecurityContextHolder.clearContext();
            response.sendError(e.getStatus().getHttpStatus().value(), e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
