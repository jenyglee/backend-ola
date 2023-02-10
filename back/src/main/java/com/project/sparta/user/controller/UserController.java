package com.project.sparta.user.controller;

import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.user.dto.UserLoginDto;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.service.UserService;
import com.project.sparta.security.jwt.JwtUtil;
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
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response){
        userService.login(userLoginDto, response);
        return new ResponseEntity(HttpStatus.OK);
    }


}
