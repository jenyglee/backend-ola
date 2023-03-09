package com.project.sparta.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmResponseDto {

    private Long alarmId;
    private String message;

    private String userNickName;

    private String readStatus;

    private Long boardId;

    public AlarmResponseDto(Long alarmId, String message, String userNickName, String readStatus, Long boardId) {
        this.alarmId = alarmId;
        this.message = message;
        this.userNickName = userNickName;
        this.readStatus = readStatus;
        this.boardId = boardId;
    }
}
