package com.project.sparta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ValidateEmailDto {
    @ApiModelProperty(example = "user0@naver.com")
    private String email;
}
