package com.project.sparta.admin.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AdminSignupDto {
    private String email;
    private String password;
    private String nickName;
    private String adminToken;
    private String phoneNumber;


    @Builder
    public AdminSignupDto(String email, String password, String nickName, String adminToken,
        String phoneNumber) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.adminToken = adminToken;
        this.phoneNumber = phoneNumber;
    }
}
