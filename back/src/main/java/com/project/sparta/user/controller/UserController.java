package com.project.sparta.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.sparta.security.dto.RegenerateTokenDto;
import com.project.sparta.security.dto.TokenDto;
import com.project.sparta.user.dto.LoginRequestDto;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.dto.ValidateEmailDto;
import com.project.sparta.user.dto.ValidateNickNameDto;
import com.project.sparta.user.service.KakaoService;
import com.project.sparta.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Api(tags = {"유저 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;


    //회원가입
    @ApiOperation(value = "회원가입",response = Join.class)
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserSignupDto signupDto){
        userService.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    //로그인
    @ApiOperation(value = "로그인",response = Join.class)
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto requestDto){
        // 어드민인지 확인하는 로직
        return userService.login(requestDto);
    }

    //카카오 로그인(redirect-uri)
    @ApiOperation(value = "카카오 로그인",response = Join.class)
    @GetMapping("/login/kakao")
    public ResponseEntity<TokenDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code, response);
    }

    //로그아웃
    @ApiOperation(value = "로그아웃",response = Join.class)
    @PostMapping("/logout")
    public ResponseEntity logout(@Validated @RequestBody TokenDto tokenRequestDto){
        // 어드민인지 확인하는 로직
        userService.logout(tokenRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 이메일 중복확인
    @PostMapping("/verify/email")
    public ResponseEntity validateEmail(@RequestBody ValidateEmailDto emailDto){
        userService.validateEmail(emailDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 닉네임 중복확인
    @PostMapping("/verify/nickname")
    public ResponseEntity validateNickName(@RequestBody ValidateNickNameDto nickNameDto){
        userService.validateNickName(nickNameDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    //토큰 재발급(클라이언트에서 Access Token이 만료되었을 때 작동)
    @PostMapping("/regenerate-token")
    public ResponseEntity<TokenDto> regenerateToken(@Validated RegenerateTokenDto tokenDto) {
        return userService.regenerateToken(tokenDto);
    }

    // TODO 새로운 비밀번호 이메일로 발송 API 제작
    // 새로운 비밀번호 이메일로 발송
    @PostMapping("/change-password")
    public void changePassword(){
    }
}
