package com.project.sparta.imgUpload.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PreSignedURLResponseDto {
    private String imageName;
    private String preSignedURL;

    @Builder
    public PreSignedURLResponseDto(String imageName, String preSignedURL) {
        this.imageName = imageName;
        this.preSignedURL = preSignedURL;
    }
}
