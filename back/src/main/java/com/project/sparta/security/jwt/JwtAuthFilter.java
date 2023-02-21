package com.project.sparta.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sparta.exception.CustomException;
import com.project.sparta.security.dto.SecurityExceptionDto;
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

    private static final String[] allowedOrigins = {
        "http://localhost:8080", "http://localhost:63342","http://127.0.0.1:5500"
    };


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        String requestOrigin = request.getHeader("Origin");
        if (isAllowedOrigin(requestOrigin)) {
            // Authorize the origin, all headers, and all methods
            (response).addHeader("Access-Control-Allow-Origin", requestOrigin);
            (response).addHeader("Access-Control-Allow-Headers", "*");
            (response).addHeader("Access-Control-Allow-Methods",
                "GET, OPTIONS, HEAD, PUT, POST, DELETE, PATCH");

            HttpServletResponse resp = response;

            // CORS handshake (pre-flight request)
            if (request.getMethod().equals("OPTIONS")) {
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                return;
            }
        }

        try {
            if (!Objects.isNull(token)) {
                if (!jwtUtil.validateToken(token)) {
                    // 유효성 검사 후 발생하는 exception에 따라서 토큰 재발급 처리해야함
                    // 클라이언트에게 에러를 보내주고
                    // 클라이언트 -> 갱신된 api호출 -> api
                    // throw new AuthenticationException();
                    response.sendError(401, "토큰이 만료되었습니다.");
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

    private boolean isAllowedOrigin(String origin) {
        for (String allowedOrigin : allowedOrigins) {
            if (origin.equals(allowedOrigin)) {
                return true;
            }
        }
        return false;
    }

}

