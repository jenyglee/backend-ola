package com.project.sparta.user.entity;

import com.project.sparta.admin.entity.Admin;
import com.project.sparta.admin.entity.StatusEnum;
import lombok.*;

import javax.persistence.*;

@Entity(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends Admin {

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String userImageUrl;

    //private List<Tag> tags = new ArrayList<>(); -> Tag 엔티티 나오면 살리기

    public User(String password, String userName, int age, String phoneNumber, String email, UserRoleEnum role, String userImageUrl, StatusEnum status) {
        this.password = password;
        this.nickName = userName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.userImageUrl = userImageUrl;
        this.status = status;
    }
}
