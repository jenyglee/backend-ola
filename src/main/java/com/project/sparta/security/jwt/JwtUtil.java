package com.project.sparta.security.jwt;

import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.security.UserDetailServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailServiceImpl userDetailServiceImpl;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    //{header BASE64 인코딩}.{JSON Claim set BASE64 인코딩}.{signature BASE64 인코딩}
    public String createToken(String username, UserRoleEnum role) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofHours(1).toMillis()); //만료시간 1시간

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);

        return BEARER_PREFIX +
                Jwts.builder()
                        .setClaims(claims)
                        .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //(1)
                        .setIssuer("sein")  //토큰 발급자
                        .setIssuedAt(now)   //발급시간
                        .setExpiration(expiration)    //만료시간
                        .signWith(signatureAlgorithm, key) //알고리즘, 시크릿 키
                        .compact();
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    //토큰 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        //StringUtils.hasText() => null값 확인(null들어있으면 false를 반환함)
        //startsWith() => String 값이 있는지 확인함(있으면 true)
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
