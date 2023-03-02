package com.project.sparta.alarm.service;


import com.project.sparta.alarm.dto.AlarmRoomDto;
import java.util.Map;

public interface AlarmService {

    AlarmRoomDto createAlarmRoom(String roomId, String roomName, int alarmMaxCnt);

    void plusUserCnt(String roomId);
    void minusUserCnt(String roomId);

    String addUser(Map<String, AlarmRoomDto> alarm, String roomId, String userName);

}


