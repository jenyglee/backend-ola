package com.project.sparta.user.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class UserSignupDto {
    private String email;
    private String password;
    private String nickName;
    private int age;
    private String phoneNumber;
    private String imageUrl;
    private List<Long> tagList;

    private boolean admin = false;

    private String adminToken="";
}