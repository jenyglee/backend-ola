package com.project.sparta.alarm2.entity;

import com.project.sparta.common.entity.Timestamped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String message;
    @Column
    private Long userId;
    @Column
    private String userNickName;
    @Column
    private Long boardId;

    @Column
    private String writerNickName;
    @Column
    private String readStatus;

    @Builder
    public Alarm(String message, Long userId, String userNickName, Long boardId,
        String readStatus, String writerNickName) {
        this.message = message;
        this.userId = userId;
        this.userNickName = userNickName;
        this.boardId = boardId;
        this.readStatus = readStatus;
        this.writerNickName = writerNickName;
    }

    public void update(Long id,String message, Long userId, String userNickName, Long boardId,
        String readStatus, String writerNickName){
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.userNickName = userNickName;
        this.boardId = boardId;
        this.readStatus = readStatus;
        this.writerNickName = writerNickName;
    }
}
