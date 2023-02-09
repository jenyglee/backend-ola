package com.project.sparta.hashtag.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
public class HashtagRequestDto {
    private String name;

    public HashtagRequestDto(String name) {
        this.name = name;
    }
}
