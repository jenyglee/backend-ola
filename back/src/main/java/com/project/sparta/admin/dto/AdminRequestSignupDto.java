package com.project.sparta.admin.dto;

import lombok.Getter;

@Getter
public class AdminRequestSignupDto {
    private String email;
    private String password;
    private String nickName;
    private String adminToken;
}
