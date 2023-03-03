package com.project.sparta.alarm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmRequetDto {

    private String message;
    private Long boardId;
    private String boardType;

}
