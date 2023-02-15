package com.project.sparta.user.dto;

import lombok.Getter;

@Getter
public class KakaoUserInfoDto {
    // Access Token을 활용해서 kakao 서버에서 사용자 정보를 가져오는데, 이 정보를 담기 위한 목적
    private Long id;
    private String email;
    private String nickName;
    private int age;
    private String phoneNumber;
    private String userImageUrl;

    public KakaoUserInfoDto(Long id, String email, String nickName) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
    }

    public KakaoUserInfoDto(Long id, String email, String nickName, int age, String phoneNumber, String userImageUrl) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.userImageUrl = userImageUrl;
    }
}
