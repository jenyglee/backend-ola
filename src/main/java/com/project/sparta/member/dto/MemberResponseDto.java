package com.project.sparta.member.dto;

import com.project.sparta.member.entity.MemberRoleEnum;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;

    private String username;

    private String password;

    private int age;

    private String email;

    private String phoneNumber;

    private MemberRoleEnum memberRoleEnum;

    @QueryProjection
    public MemberResponseDto(Long id, String username, String password,int age, String email, String phoneNumber, MemberRoleEnum memberRoleEnum){
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.memberRoleEnum = memberRoleEnum;
    }
}
