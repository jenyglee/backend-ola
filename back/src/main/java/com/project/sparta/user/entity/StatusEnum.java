package com.project.sparta.user.entity;

import com.project.sparta.user.entity.UserRoleEnum;

public enum StatusEnum {

    USER_WITHDRAWAL, //탈퇴 회원
    USER_REGISTERED, // 가입된 회원
    ADMIN_REGISTERED, // 가입된 관리자
    ADMIN_EXPIRED // 만료된 관리자

}
