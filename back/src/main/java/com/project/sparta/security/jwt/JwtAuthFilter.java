package com.project.sparta.security.jwt;

import static com.project.sparta.exception.api.Status.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.security.dto.SecurityExceptionDto;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        try {
            if (!Objects.isNull(token)) {
                if (!jwtUtil.validateToken(token)) {
                    response.sendError(401, "토큰이 만료되었습니다.");
                    throw new CustomException(INVALID_TOKEN);
                }
                Authentication auth = jwtUtil.getAuthenticationByAccessToken(token);
                String isLogout = redisTemplate.opsForValue().get(auth.getName());

                if (!ObjectUtils.isEmpty(isLogout)) {
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
            String json = new ObjectMapper().writeValueAsString(
                new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

