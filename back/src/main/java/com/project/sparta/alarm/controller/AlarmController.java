package com.project.sparta.alarm.controller;

import com.project.sparta.alarm.dto.AlarmDto;
import com.project.sparta.alarm.dto.AlarmMessageDto;
import com.project.sparta.alarm.dto.AlarmRoomDto;
import com.project.sparta.alarm.dto.AlarmMap;
import com.project.sparta.alarm.service.AlarmService;
import com.project.sparta.chat.dto.ChatDto;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AlarmController {

    private final AlarmService alarmService;

    private final SimpMessageSendingOperations template;

    @PostConstruct
    public void init() {
        createAlarmRoom();
    }

    //1. 알람 구독 채널을 파고
    public void createAlarmRoom() {

        AlarmRoomDto alarm;

        alarm = alarmService.createAlarmRoom("1","alarm", 100000);

        log.info("CREATE Alarm Room [{}]", alarm);
    }

    //2. 모든 클라이언트가 로그인 시 알람 채널을 구독하게 됨
    @MessageMapping("/alarm/enterUser")
    public void enterUser(@Payload AlarmDto alarm, SimpMessageHeaderAccessor headerAccessor) {

        //채팅방 유저 + 1
        alarmService.plusUserCnt(alarm.getRoomId());

        //채팅방에 유저 추가 및 UserUUID 반환
        String userUUID = alarmService.addUser(AlarmMap.getInstance().getAlarmRooms(),
            alarm.getRoomId(), alarm.getSender());

        //반환 결과를 socket session에 userUUID로 저장
        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", alarm.getRoomId());

        template.convertAndSend("/sub/alarm/room/" + alarm.getRoomId(), alarm);
    }

    //3. 요구사항 1) 누군가 로그인한 유저의 글에 댓글을 작성하게 될 경우 알람이 울림 ( 유저가 알림을 받을 때 )
    //4. 요구사항 2) 내 친구가 글을 작성했을 경우 알람이 울림
    public void alarmSendMessage(String roomId, AlarmMessageDto messageDto) {
        System.out.println("실행되나요??");

        template.convertAndSend("/sub/alarm/room/" + roomId, messageDto);
    }
    //5. 내가 로그아웃을 했을 경우에 채널과 연결이 끊김 or 채널이 끊기지 않고 계속해서 연결?? -> 만약에 로그아웃 했을 경우에는 그럼 어떻게 되는거징?


    // 사용자 마다 각자의 방을 만든다. => 방이름을 유저 nickName
    // 화면단에서 게시글의 주인의 방을 구독하고 -> 메세지를 보냄
    // 그럼 해당 메세지를 받는 사용자는 로그인하고 나서 자신의 방에 있는 메세지를 받아 볼 수 있음
}





