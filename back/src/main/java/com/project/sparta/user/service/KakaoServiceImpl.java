package com.project.sparta.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sparta.security.dto.TokenDto;
import com.project.sparta.security.jwt.JwtUtil;
import com.project.sparta.user.dto.KakaoUserInfoDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.repository.UserRepository;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String kakaoLogin(String code, HttpServletResponse response)
        throws JsonProcessingException {
        //1. '인가 코드'로 Access Token 요청
        String kakaoAccessToken = getToken(code);
        //2. 토큰으로 카카오 API 호출 : Access Token으로 카카오 사용자 정보 가져오기
        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(kakaoAccessToken);

        //3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfoDto);

        //4. JWT 토큰 반환
        String accessToken = jwtUtil.generateAccessToken(kakaoUser.getEmail(), kakaoUser.getRole(), kakaoUser.getGradeEnum(), kakaoUser.getNickName(), kakaoUser.getUserImageUrl());
        String refreshToken = jwtUtil.generateRefreshToken(kakaoUser.getEmail(),
            kakaoUser.getRole());

        TokenDto tokenDto = new TokenDto(
            accessToken,
            refreshToken,
            kakaoUser.getNickName()
        );
        redisTemplate.opsForValue().set(
            kakaoUser.getEmail(),
            refreshToken,
            JwtUtil.REFRESH_TOKEN_TIME,
            TimeUnit.MILLISECONDS
        );

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtUtil.AUTHORIZATION_HEADER, tokenDto.getAccessToken());

        return "로그인 완료";
    }

    // '인가 코드'로 Access Token 요청
    @Override
    public String getToken(String code) throws JsonProcessingException {
        //HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "caa41730845bebbbdfd06ada0309e628");
        body.add("redirect_uri", "http://sparta-ola-website.s3-website.ap-northeast-2.amazonaws.com/");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body,
            headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
        );

        // HTTP 응답(JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 토큰으로 카카오 API 호출 : Access Token으로 카카오 사용자 정보 가져오기
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        System.out.println("accessToken = " + accessToken);
        //Http Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //Http 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
            "https://kapi.kakao.com/v2/user/me", // 401 unAuthorize
            HttpMethod.POST,
            kakaoUserInfoRequest,
            String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        //  age, phoneNumber, userImageUrl 는 어떻게 가져와야하는지 알아보기(재원)

        long id = jsonNode.get("id").asLong();
        System.out.println("responseBody id = " + id);

        String nickname = jsonNode.get("properties")
            .get("nickname").asText();
        System.out.println("nickname = " + nickname);

        // 🔥이메일 수집을 동의 안해놓은 상태로 테스트로 내 계정(리재워니)로 카카오로그인을 해버렸고, 이후 동의창이 새로 떠줘야 하는데 뜨질 않아서 이메일 정보를 가져올 수 없게되었다.
        // 🔥엎친데 덮친격으로 DB에 가입된 회원을 삭제하면 될 줄 알았는데 DB에서는 사라졌으나 동의창이 여전히 뜨지 않고 메인화면으로 이동해버린다.
        String email = jsonNode.get("kakao_account")
            .get("email").asText();
        System.out.println("email = " + email);

        log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);

        return new KakaoUserInfoDto(id, email, nickname);
    }

    // 필요시에 회원가입
    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
            .orElse(null);
        if (kakaoUser == null) {
            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            String kakaoEmail = kakaoUserInfo.getEmail();
            User sameEmailUser = userRepository.findByEmail(kakaoEmail).orElse(null);
            if (sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                // 기존 회원정보에 카카오 Id 추가
                kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
            } else {
                // 신규 회원가입
                // password: random UUID
                String password = UUID.randomUUID().toString();
                String encodedPassword = encoder.encode(password);

                // email: kakao email
                String email = kakaoUserInfo.getEmail();

                kakaoUser = new User(kakaoId, kakaoUserInfo.getNickName(), encodedPassword, email,
                    kakaoUserInfo.getAge(), kakaoUserInfo.getPhoneNumber(),
                    kakaoUserInfo.getUserImageUrl());
            }

            userRepository.save(kakaoUser);
        }
        return kakaoUser;
    }
}