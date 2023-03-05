package com.project.sparta.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRequestDto {

    private Long roomId;

    private String title;

    private String chatStatus;

    private int chatMemCnt;
}
