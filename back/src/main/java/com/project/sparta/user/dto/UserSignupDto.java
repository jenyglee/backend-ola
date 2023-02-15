package com.project.sparta.user.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
public class UserSignupDto {
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "sparta@sparta.com 형식으로 입력해주세요.")
    private String email;
    @Pattern(regexp = "[\\w0-9~!@#$%^&*()_+|<>?:{}]{8,15}", message = "최소 8자 이상, 15자 이하 알파벳 대소문자(a~z, A~Z), 숫자(0~9)를 혼합하여 입력해주세요.")
    private String password;
    @Pattern(regexp = "[a-z0-9]{4,10}", message = "최소 4자 이상, 10자 이하 알파벳 소문자(a~z), 숫자(0~9)를 혼합하여 입력해주세요.")
    private String nickName;
    private int age;
    @Pattern(regexp = "/^010-?([0-9]{3,4})-?([0-9]{4})$/", message = "- 를 포함하여 입력해주세요.")
    private String phoneNumber;
    private String imageUrl;
    private List<Long> tagList;

    private boolean admin = false;

    private String adminToken="";
}