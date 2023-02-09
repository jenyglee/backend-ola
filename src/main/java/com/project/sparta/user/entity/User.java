package com.project.sparta.user.entity;

import com.project.sparta.admin.entity.Admin;
import com.project.sparta.admin.entity.Root;
import com.project.sparta.admin.entity.StatusEnum;
import lombok.*;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;

@Entity(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends Root {

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String userImageUrl;

    @Enumerated(value=EnumType.STRING)
    protected UserGradeEnum gradeEnum;


    //private List<Tag> tags = new ArrayList<>(); -> Tag 엔티티 나오면 살리기

    @Builder(builderMethodName = "userBuilder")

    public User(String email, String password, String nickName, UserRoleEnum role, StatusEnum status,
                int age, String phoneNumber, String userImageUrl, UserGradeEnum gradeEnum) {

        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.role = role;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.userImageUrl = userImageUrl;
        this.gradeEnum = gradeEnum;
        this.status = status;
    }

}
