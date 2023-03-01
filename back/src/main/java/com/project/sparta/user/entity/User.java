package com.project.sparta.user.entity;

import com.project.sparta.common.entity.Timestamped;
import com.project.sparta.communityBoard.entity.CommunityBoard;

import java.util.ArrayList;

import lombok.*;

import java.util.List;
import javax.persistence.*;

@Entity(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long kakaoId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;
    private int age;

    private String phoneNumber;

    private String userImageUrl;

    @Enumerated(value = EnumType.STRING)
    private UserGradeEnum gradeEnum;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;

    @Column
    private int enterCount = 0;

    @Column
    private int makeCount = 0;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private final List<CommunityBoard> communityBoards = new ArrayList<>();

    public void updateUserTags(List<UserTag> userTagList){
        this.tags = userTagList;
    }

    public void getUserTagList(List<UserTag> userTagList){
        this.tags = userTagList;
    }


    @Builder(builderClassName = "user", builderMethodName = "userBuilder")
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

    @Builder(builderClassName = "admin", builderMethodName = "adminBuilder")
    public User(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.role = UserRoleEnum.ADMIN;
        this.status = StatusEnum.ADMIN_REGISTERED;
    }

    // 카카오 회원가입 생성자
    // ❓카카오 회원가입 누르면 회원가입이 바로 되어버릴텐데, tags에 해시태그 등록은 어느 시점에서 해야할까?
    public User(Long kakaoId, String nickName, String password, String email, int age, String phoneNumber, String userImageUrl) {
        this.kakaoId = kakaoId;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.userImageUrl = userImageUrl;
        this.role = UserRoleEnum.USER;
        this.gradeEnum = UserGradeEnum.MOUNTAIN_CHILDREN;
        this.status = StatusEnum.USER_REGISTERED;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void changeGrade(UserGradeEnum gradeEnum){
        this.gradeEnum = gradeEnum;
    }

    public void changeStatus(StatusEnum status){
        this.status = status;
    }


    public void isUserId(Long userId) {
        // TODO ID 체크를 객체지향적으로 쓰기
    }
}
