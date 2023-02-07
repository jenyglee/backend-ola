package com.project.sparta.user.dto;

import com.project.sparta.user.entity.UserRoleEnum;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponseDto {
    private Long id;

    private String username;

    private String password;

    private int age;

    private String email;

    private String phoneNumber;

    private UserRoleEnum userRoleEnum;

    @QueryProjection
    public UserResponseDto(Long id, String username, String password, int age, String email, String phoneNumber, UserRoleEnum userRoleEnum){
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRoleEnum = userRoleEnum;
    }
}
