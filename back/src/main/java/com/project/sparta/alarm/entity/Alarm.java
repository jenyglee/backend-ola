package com.project.sparta.alarm.entity;

import com.project.sparta.common.entity.Timestamped;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
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

    public void updateReadStatus(String readStatus){
        this.readStatus = readStatus;
    }
}
