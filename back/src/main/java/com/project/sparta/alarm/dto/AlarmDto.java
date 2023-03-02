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
    private String roomId; // 방 번호
    private String sender; // 구독하는 사람
}
