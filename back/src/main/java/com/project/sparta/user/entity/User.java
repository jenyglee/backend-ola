package com.project.sparta.user.entity;

import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.communityBoard.entity.CommunityBoard;

import java.util.ArrayList;

import lombok.*;

import java.util.List;
import javax.persistence.*;

@Entity(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String userImageUrl;

    @Enumerated(value = EnumType.STRING)
    private UserGradeEnum gradeEnum;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;

    @Column
    private Long enterCount;

    @Column
    private Long makeCount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTag> tags = new ArrayList<>();

    @Column(nullable = false)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommunityBoard> communityBoards = new ArrayList<>();

    public void updateUserTags(List<UserTag> userTagList){
        this.tags = userTagList;
    }

    @Builder
    public User(String email, String password, String nickName, int age, String phoneNumber, String userImageUrl, List<UserTag> tags) {
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.role = UserRoleEnum.USER;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.userImageUrl = userImageUrl;
        this.gradeEnum = UserGradeEnum.MOUNTAIN_CHILDREN;
        this.status = StatusEnum.USER_REGISTERED;
        this.tags = tags;
    }

     @Builder()
     public User(String email, String password, String nickName, int age, String phoneNumber, String userImageUrl) {
         this.password = password;
         this.nickName = nickName;
         this.email = email;
         this.role = UserRoleEnum.USER;
         this.age = age;
         this.phoneNumber = phoneNumber;
         this.userImageUrl = userImageUrl;
         this.gradeEnum = UserGradeEnum.MOUNTAIN_CHILDREN;
         this.status = StatusEnum.USER_REGISTERED;
     }

    // //테스트용
    // public User(String email, String password, String nickName, String userImageUrl) {
    //     this.password = password;
    //     this.nickName = nickName;
    //     this.email = email;
    //     this.role = UserRoleEnum.ADMIN;
    //     this.userImageUrl = userImageUrl;
    //     this.status = StatusEnum.ADMIN_REGISTERED;
    // }
}
