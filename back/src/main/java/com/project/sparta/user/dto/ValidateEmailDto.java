package com.project.sparta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ValidateEmailDto {
    @ApiModelProperty(example = "user0@naver.com")
    private String email;
}
