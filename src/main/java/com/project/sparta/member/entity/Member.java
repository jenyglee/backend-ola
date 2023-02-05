package com.project.sparta.member.entity;

import com.project.sparta.entity.Timestamped;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends Timestamped {

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
    private MemberRoleEnum role;

    @Builder
    public Member(String password, String username, int age, String phoneNumber, String email, MemberRoleEnum role) {
        this.password = password;
        this.username = username;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

}
