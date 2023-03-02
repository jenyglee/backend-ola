package com.project.sparta.alarm.service;

import com.project.sparta.alarm.dto.AlarmMessageDto;
import com.project.sparta.alarm.dto.AlarmRoomDto;
import com.project.sparta.alarm.dto.AlarmMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AlarmServiceImpl implements AlarmService{

    @Override
    public AlarmRoomDto createAlarmRoom(String roomName, String alarmType) {

        AlarmRoomDto alarmRoom = AlarmRoomDto.builder()
            .roomName(roomName)
            .alarmType(alarmType) // 최대 인원수 제한
            .build();

        alarmRoom.setUserList(new ConcurrentHashMap<String, String>());

        // map 에 채팅룸 아이디와 만들어진 채팅룸을 저장
        AlarmMap.getInstance().getAlarmRooms().put(alarmRoom.getRoomId(), alarmRoom);

        return alarmRoom;
    }

    // 채팅방 유저 리스트에 유저 추가
    @Override
    public String addUser(Map<String, AlarmRoomDto> alarm, String roomId, String userName){
        AlarmRoomDto alarmRoom = alarm.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        //아이디 중복 확인 후 userList 에 추가
        alarmRoom.getUserList().put(userUUID, userName);

        // hashmap 에서 concurrentHashMap 으로 변경
        ConcurrentHashMap<String, String> userList = (ConcurrentHashMap<String, String>)alarmRoom.getUserList();
        userList.put(userUUID, userName);

        return userUUID;
    }

}
