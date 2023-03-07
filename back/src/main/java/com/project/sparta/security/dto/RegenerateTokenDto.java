package com.project.sparta.security.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class RegenerateTokenDto {

    @ApiModelProperty(example = "유효한 리프레시 토큰 값")
    private String refreshToken;
}
