package com.project.sparta.user.dto;

import com.project.sparta.user.entity.UserRoleEnum;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private Long id;

    private String nickName;

    private String password;

    private int age;

    private String email;

    private String phoneNumber;

    private UserRoleEnum userRoleEnum;

    @QueryProjection
    public UserResponseDto(Long id, String nickName, String password, int age, String email, String phoneNumber, UserRoleEnum userRoleEnum){
        this.id = id;
        this.nickName = nickName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRoleEnum = userRoleEnum;
    }
}
