package com.project.sparta.alarm.dto;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmMap {

    private static AlarmMap AlarmMap = new AlarmMap();
    private ConcurrentMap<String, AlarmRoomDto> alarmRooms = new ConcurrentHashMap<>();

    private AlarmMap(){}

    public static AlarmMap getInstance(){
        return AlarmMap;
    }
}
