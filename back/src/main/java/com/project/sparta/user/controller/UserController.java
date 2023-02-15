package com.project.sparta.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.sparta.security.dto.RegenerateTokenDto;
import com.project.sparta.security.dto.TokenDto;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.security.jwt.JwtUtil;
import com.project.sparta.user.dto.*;
import com.project.sparta.user.service.KakaoService;
import com.project.sparta.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;


    //회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserSignupDto signupDto){
        userService.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto requestDto){
        // 어드민인지 확인하는 로직
        return userService.login(requestDto);
    }

    //TODO 카카오 로그인 일단 킵
    @GetMapping("/login/kakao")
    public ResponseEntity kakaoLogin(@RequestParam String code, HttpServletResponse response) throws IOException {
        // code: 카카오 서버로부터 받은 인가 코드
        System.out.println("code = " + code);
        String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 자동으로 세팅하게 된다.
        // Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        // cookie.setPath("/");
        // response.addCookie(cookie);
        // response.sendRedirect("http://localhost:63342/front/template/j-1_noticeBoardAllList.html");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://www.naver.com"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity login(@Validated @RequestBody TokenDto tokenRequestDto){
        // 어드민인지 확인하는 로직
        userService.logout(tokenRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 이메일 중복확인
    @PostMapping("/check_emails")
    public ResponseEntity validateEmail(@RequestBody ValidateEmailDto emailDto){
        userService.validateEmail(emailDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 닉네임 중복확인
    @PostMapping("/check_nicknames")
    public ResponseEntity validateNickName(@RequestBody ValidateNickNameDto nickNameDto){
        userService.validateNickName(nickNameDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/myInfo")
    public ResponseEntity getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.getMyInfo(userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/regenerateToken")
    public ResponseEntity<TokenDto> regenerateToken(@Validated RegenerateTokenDto tokenDto) {
        return userService.regenerateToken(tokenDto);
    }
}
