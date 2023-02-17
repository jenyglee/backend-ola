package com.project.sparta.user.dto;

import lombok.Getter;

@Getter
public class UpgradeRequestDto {
    private String grade;

    public UpgradeRequestDto(String grade) {
        this.grade = grade;
    }
}
