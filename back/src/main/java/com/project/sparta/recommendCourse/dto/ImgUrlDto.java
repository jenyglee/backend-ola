package com.project.sparta.recommendCourse.dto;

import lombok.Getter;

@Getter
public class ImgUrlDto {
    private String url;

    public ImgUrlDto(String url) {
        this.url = url;
    }
}
