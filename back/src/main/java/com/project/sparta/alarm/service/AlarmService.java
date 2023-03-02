package com.project.sparta.alarm.service;


import com.project.sparta.alarm.dto.AlarmRoomDto;
import java.util.Map;

public interface AlarmService {

    AlarmRoomDto createAlarmRoom(String roomName, String alarmType);

    String addUser(Map<String, AlarmRoomDto> alarm, String roomName, String userName);

}


