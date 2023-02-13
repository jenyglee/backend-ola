package com.project.sparta.user.service;


import com.project.sparta.user.dto.LoginRequestDto;
import com.project.sparta.user.dto.LoginResponseDto;
import com.project.sparta.user.dto.UserSignupDto;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    void signup(UserSignupDto signupDto);
    LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response);
}
