package com.project.sparta.user.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(value=EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    private String userImageUrl;

    //private List<Tag> tags = new ArrayList<>(); -> Tag 엔티티 나오면 살리기

    @Column(nullable = false)
    private String status;

    @Builder
    public User(Long id, String password, String userName, int age, String phoneNumber, String email, UserRoleEnum role, String userImageUrl, String status) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.userImageUrl = userImageUrl;
        this.status = status;
    }
}
