package com.project.sparta.admin.entity;

import com.project.sparta.user.entity.UserRoleEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long Id;

    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false)
    protected String name;

    @Enumerated(value=EnumType.STRING)
    protected UserRoleEnum role;

    @Enumerated(value=EnumType.STRING)
    protected StatusEnum status;

    @Builder
    public Admin(String email, String password, String name, UserRoleEnum role, StatusEnum status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.status = status;
    }
}
