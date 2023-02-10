package com.project.sparta.admin.entity;

import com.project.sparta.user.entity.UserRoleEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends Root {

    @Column
    private String adminPassword = "07B4925039BE4219C76865C5CCB87466";


    @Builder(builderMethodName = "aminBuilder")
    public Admin(String email, String password, String nickName, UserRoleEnum role, StatusEnum status, String adminPassword) {
        super(email, password, nickName, role, status);
        this.adminPassword = adminPassword;
    }

}
