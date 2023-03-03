package com.project.sparta.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminSignupDto {
    private String email;
    private String password;
    private String nickName;
    private String adminToken;


    @Builder
    public AdminSignupDto(String email, String password, String nickName, String adminToken) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.adminToken = adminToken;
    }
}
