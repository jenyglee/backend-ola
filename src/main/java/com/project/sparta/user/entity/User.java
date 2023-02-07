package com.project.sparta.user.entity;

import lombok.*;

import javax.persistence.*;

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
    private String username;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, unique = true)
    private String phoneNumber;


    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(value=EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    public User(String password, String username, int age, String phoneNumber, String email, UserRoleEnum role) {
        this.password = password;
        this.username = username;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

}
