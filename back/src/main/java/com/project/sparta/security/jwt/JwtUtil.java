package com.project.sparta.security.jwt;

import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.security.UserDetailServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
//
// @Component
// @RequiredArgsConstructor
// public class JwtUtil {
//
//     private final UserDetailServiceImpl userDetailServiceImpl;
//
//     public static final String AUTHORIZATION_HEADER = "Authorization";
//
//     private static final String BEARER_PREFIX = "Bearer ";
//
//     @Value("${jwt.secret}")
//     private String secretKey;
//
//     private Key key;
//
//     private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//     @PostConstruct
//     public void init() {
//         byte[] bytes = Base64.getDecoder().decode(secretKey);
//         key = Keys.hmacShaKeyFor(bytes);
//     }
//
//     //{header BASE64 인코딩}.{JSON Claim set BASE64 인코딩}.{signature BASE64 인코딩}
//     public String createToken(String email, UserRoleEnum role) {
//         Date now = new Date();
//         Date expiration = new Date(now.getTime() + Duration.ofHours(1).toMillis()); //만료시간 1시간
//
//         Claims claims = Jwts.claims().setSubject(email);
//         claims.put("role", role);
//
//         return BEARER_PREFIX +
//                 Jwts.builder()
//                         .setClaims(claims)
//                         .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //(1)
//                         .setIssuer("sein")  //토큰 발급자
//                         .setIssuedAt(now)   //발급시간
//                         .setExpiration(expiration)    //만료시간
//                         .signWith(signatureAlgorithm, key) //알고리즘, 시크릿 키
//                         .compact();
//     }
//
//     // 토큰의 유효성 + 만료일자 확인
//     public boolean validateToken(String token) {
//         try {
//             Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//             return !claims.getExpiration().before(new Date());
//         } catch (Exception e) {
//             return false;
//         }
//     }
//
//     //토큰 값 가져오기
//     public String resolveToken(HttpServletRequest request) {
//         String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//
//         //StringUtils.hasText() => null값 확인(null들어있으면 false를 반환함)
//         //startsWith() => String 값이 있는지 확인함(있으면 true)
//         if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//             return bearerToken.substring(7);
//         }
//         return null;
//     }
//
//     // 토큰에서 회원 정보 추출
//     public String getUserPk(String token) {
//         return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
//     }
//
//     // JWT 토큰에서 인증 정보 조회
//     public Authentication getAuthentication(String token) {
//         UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(getUserPk(token));
//         return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//     }
// }

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsService userDetailsService;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
