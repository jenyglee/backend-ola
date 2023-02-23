package com.project.sparta.user.dto;

import com.project.sparta.user.entity.StatusEnum;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserListResponseDto {
    private Long id;
    private String nickName;
    private String email;
    private String phoneNumber;
    private UserGradeEnum grade;
    private LocalDateTime createdAt;
    private StatusEnum status;

    @Builder
    public UserListResponseDto(Long id, String nickName, String email, String phoneNumber,
        UserGradeEnum grade, LocalDateTime createdAt, StatusEnum status) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.createdAt = createdAt;
        this.status = status;
    }
}
