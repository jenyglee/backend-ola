package com.project.sparta.hashtag.dto;

import lombok.Getter;

@Getter
public class HashtagResponseDto {
    private String name;

    public HashtagResponseDto(String name) {
        this.name = name;
    }
}
