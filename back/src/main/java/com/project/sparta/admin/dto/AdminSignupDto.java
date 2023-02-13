package com.project.sparta.admin.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AdminSignupDto {
    private String email;
    private String password;
    private String nickName;
    private String adminToken;
}
