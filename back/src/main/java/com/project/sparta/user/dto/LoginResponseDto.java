package com.project.sparta.user.dto;

import com.project.sparta.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private UserRoleEnum role;

    public LoginResponseDto(UserRoleEnum role) {
        this.role = role;
    }
}
