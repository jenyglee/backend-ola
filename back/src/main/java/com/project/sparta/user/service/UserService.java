package com.project.sparta.user.service;


import com.project.sparta.user.dto.UserLoginDto;
import com.project.sparta.user.entity.UserRoleEnum;

public interface UserService {

    UserRoleEnum login(UserLoginDto userLoginDto);
}
