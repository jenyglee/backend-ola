package com.project.sparta.alarm.dto;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@Data
@AllArgsConstructor
public class AlarmRoomDto {
    @NotNull
    private String roomId;      //알람방 아이디
    private String roomName;    //알람방 이름
    private long userCount;     //알람방 인원수
    public ConcurrentMap<String, String> userList = new ConcurrentHashMap<>();
}
