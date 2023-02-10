package com.project.sparta.user.dto;

import com.project.sparta.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserSignupDto {
    private String email;
    private String password;
    private String nickName;
    private int age;
    private String phoneNumber;
    private String imageUrl;
}
// String email, String password, String nickName, UserRoleEnum role, StatusEnum status,
// int age, String phoneNumber, String userImageUrl, UserGradeEnum gradeEnum