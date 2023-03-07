package com.project.sparta.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserGradeDto {
    @ApiModelProperty(example = "0") // Mountatin Children
    private int grade;
}
