package com.project.sparta.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.sparta.security.dto.RegenerateTokenDto;
import com.project.sparta.security.dto.TokenDto;
import com.project.sparta.user.dto.LoginRequestDto;
import com.project.sparta.user.dto.MailDto;
import com.project.sparta.user.dto.SendMailRequestDto;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.dto.ValidateEmailDto;
import com.project.sparta.user.dto.ValidateNickNameDto;
import com.project.sparta.user.service.KakaoService;
import com.project.sparta.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"유저 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;


    //회원가입
    @ApiOperation(value = "회원가입", response = Join.class)
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody
    @ApiParam(value = "회원가입 작성 값", required = true)
    @Validated UserSignupDto signupDto) {
        userService.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    //로그인
    @ApiOperation(value = "로그인", response = Join.class)
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody
    @ApiParam(value = "로그인 작성 값", required = true) LoginRequestDto requestDto) {
        return userService.login(requestDto);
    }

    //카카오 로그인(redirect-uri)
    @ApiOperation(value = "카카오 로그인", response = Join.class)
    @GetMapping("/login/kakao")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws IOException {
        System.out.println("code = " + code);
        kakaoService.kakaoLogin(code, response);
        return "로그인 완료";
    }

    //로그아웃
    @ApiOperation(value = "로그아웃", response = Join.class)
    @PostMapping("/logout")
    public ResponseEntity logout(
        @Validated
        @RequestBody
        @ApiParam(value = "로그아웃 토큰", required = true) TokenDto tokenRequestDto) {
        // 어드민인지 확인하는 로직
        userService.logout(tokenRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 이메일 중복확인
    @ApiOperation(value = "이메일 중복확인", response = Join.class)
    @PostMapping("/verify/email")
    public ResponseEntity validateEmail(
        @RequestBody
        @ApiParam(value = "중복확인 이메일 값", required = true) ValidateEmailDto emailDto) {
        userService.validateEmail(emailDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 닉네임 중복확인
    @ApiOperation(value = "닉네임 중복확인", response = Join.class)
    @PostMapping("/verify/nickname")
    public ResponseEntity validateNickName(
        @RequestBody
        @ApiParam(value = "중복확인 닉네임 값", required = true) ValidateNickNameDto nickNameDto) {
        userService.validateNickName(nickNameDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    //토큰 재발급(클라이언트에서 Access Token이 만료되었을 때 작동)
    @ApiOperation(value = "토큰 재발급", response = Join.class)
    @PostMapping("/token/regenerate")
    public ResponseEntity<TokenDto> regenerateToken(
        @RequestBody
        @Validated
        @ApiParam(value = "갱신할 리프레쉬 토큰 값", required = true) RegenerateTokenDto tokenDto) {
        return userService.regenerateToken(tokenDto);
    }

    // 비밀번호 재발급 Email과 name의 일치여부를 check하는 컨트롤러
    @ApiOperation(value = "토큰 재발급", response = Join.class)
    @GetMapping("/check/findPw")
    public @ResponseBody Map<String, Boolean> pw_find(@RequestParam String email, @RequestParam String nickName){
        Map<String,Boolean> json = new HashMap<>();
        boolean pwFindCheck = userService.userEmailCheck(email, nickName);
        System.out.println(pwFindCheck);
        json.put("check", pwFindCheck);
        return json;
    }

    // 새로운 비밀번호 이메일로 발송
    @PostMapping("/check/findPw/sendEmail")
    public void sendMail(@RequestBody SendMailRequestDto requestDto){
        MailDto dto = userService.createMailAndChangePassword(requestDto.getEmail(), requestDto.getNickName());
        userService.sendMail(dto);
    }
}
