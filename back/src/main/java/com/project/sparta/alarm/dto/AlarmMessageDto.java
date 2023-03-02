package com.project.sparta.alarm.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmMessageDto {

    private String title;
    private String writerNickName;  //메세지 받는 사람
    private String sendNickName;    //메세지를 보내는 사람의 닉네임
    private String boardType;   //글의 종류
    private LocalDateTime time; // 발송 시간
}
