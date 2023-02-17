
package com.project.sparta.user.dto;

import lombok.Builder;
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

    @Builder
    public UserSignupDto(String email, String password, String nickName, int age,
        String phoneNumber,
        String imageUrl, List<Long> tagList) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.tagList = tagList;
    }
}


