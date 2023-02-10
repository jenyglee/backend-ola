package com.project.sparta.user.service;


import com.project.sparta.user.dto.UserLoginDto;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.entity.UserRoleEnum;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    void signup(UserSignupDto signupDto);
    void login(UserLoginDto userLoginDto, HttpServletResponse response);
}
