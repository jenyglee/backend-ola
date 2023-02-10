package com.project.sparta.user.service;


import com.project.sparta.user.dto.UserLoginDto;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.entity.UserRoleEnum;

public interface UserService {

    void signup(UserSignupDto signupDto);
    UserRoleEnum login(UserLoginDto userLoginDto);
}
