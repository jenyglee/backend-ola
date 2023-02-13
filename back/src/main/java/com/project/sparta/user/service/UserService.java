package com.project.sparta.user.service;


import com.project.sparta.user.dto.*;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    void signup(UserSignupDto signupDto);
    LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response);

    void validateEmail(ValidateEmailDto emailDto);

    void validateNickName(ValidateNickNameDto nickNameDto);

    void getMyInfo(Long id);
}
