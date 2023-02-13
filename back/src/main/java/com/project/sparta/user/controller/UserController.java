package com.project.sparta.user.controller;

import com.project.sparta.user.dto.LoginRequestDto;
import com.project.sparta.user.dto.LoginResponseDto;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
