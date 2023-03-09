package com.project.sparta.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusDto {
    @ApiModelProperty(example = "1") // registered
    private int status;

}
