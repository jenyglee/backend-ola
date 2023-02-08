package com.project.sparta.admin.entity;

import com.project.sparta.user.entity.UserRoleEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long Id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String adminName;

    @Column(nullable = false)
    private String password;



    @Builder
    public Admin(String email, String adminName, String password) {
        this.email = email;
        this.adminName = adminName;
        this.password = password;
    }
}
