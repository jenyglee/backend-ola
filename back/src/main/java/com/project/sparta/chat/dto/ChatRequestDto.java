package com.project.sparta.chat.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRequestDto {
    @ApiModelProperty(example = "1")
    private Long roomId;
    @ApiModelProperty(example = "스웨거로 전달할 타이틀")

    private String title;
    @ApiModelProperty(example = "Y")
    private String chatStatus;

    @ApiModelProperty(example = "5")
    private int chatMemCnt;
}
