package com.project.sparta.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDto {
    private String roomName; // 방 이름
    private String sender; // 채팅을 보낸 사람
    private String message; //메세지
}
