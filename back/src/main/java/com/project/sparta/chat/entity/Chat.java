package com.project.sparta.chat.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue
    private Long Id;

    private String title;

    private String chatStatus;

    private int chatMemCnt;

    @Builder
    public Chat(Long id, String title, String chatStatus, int chatMemCnt) {
        Id = id;
        this.title = title;
        this.chatStatus = chatStatus;
        this.chatMemCnt = chatMemCnt;
    }
}
