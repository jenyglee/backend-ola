package com.project.sparta.admin.entity;

import com.project.sparta.user.entity.Timestamped;
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
public class Admin extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long Id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(value=EnumType.STRING)
    private UserRoleEnum role;

    @Enumerated(value=EnumType.STRING)
    private StatusEnum status;


    @Builder
    public Admin(String email, String password, String name, UserRoleEnum role, StatusEnum status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.status = status;
    }
}
