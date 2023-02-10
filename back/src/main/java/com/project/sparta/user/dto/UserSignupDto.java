package com.project.sparta.user.dto;

import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.entity.UserTag;
import lombok.Getter;

import java.util.ArrayList;
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
}