package com.project.sparta.admin.dto;

import lombok.Getter;

@Getter
public class UserGradeDto {
    private int grade;

    public UserGradeDto(int grade) {
        this.grade = grade;
    }
}


