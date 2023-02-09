package com.project.sparta.user.entity;

import com.project.sparta.admin.entity.Admin;
import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityComment.entity.CommunityComment;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommunityBoard> communityBoards = new ArrayList<>();

    //private List<Tag> tags = new ArrayList<>(); -> Tag 엔티티 나오면 살리기

    public User(String password, String nickName, int age, String phoneNumber, String email, UserRoleEnum role, String userImageUrl, StatusEnum status) {
        this.password = password;
        this.nickName = nickName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.userImageUrl = userImageUrl;
        this.status = status;
    }
}
