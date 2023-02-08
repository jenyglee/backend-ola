package com.project.sparta.user.controller;

import com.project.sparta.user.dto.UserLoginDto;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.service.UserService;
import com.project.sparta.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public HttpStatus login(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response){
        UserRoleEnum role = userService.login(userLoginDto);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(userLoginDto.getNickName(), role));
        return HttpStatus.OK;
    }
}
