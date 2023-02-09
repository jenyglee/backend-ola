package com.project.sparta.user.entity;

import com.project.sparta.admin.entity.Admin;
import com.project.sparta.admin.entity.Root;
import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityComment.entity.CommunityComment;
import java.util.ArrayList;
import lombok.*;
import org.springframework.context.annotation.Bean;
import java.util.List;
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

    @Column(nullable = false)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<CommunityBoard> communityBoards = new ArrayList<>();

    @Enumerated(value=EnumType.STRING)
    protected UserGradeEnum gradeEnum;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTag> tags = new ArrayList<>();

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
