package com.project.sparta.user.controller;

import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.dto.*;
import com.project.sparta.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserSignupDto signupDto){
        userService.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        // 어드민인지 확인하는 로직
        LoginResponseDto myRole = userService.login(requestDto, response);
        return new ResponseEntity(myRole, HttpStatus.OK);
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
        userService.getMyInfo(userDetails.getUser().getId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
