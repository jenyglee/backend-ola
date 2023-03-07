package com.project.sparta.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserStatusDto {
    @ApiModelProperty(example = "1") // registered
    private int status;

}
