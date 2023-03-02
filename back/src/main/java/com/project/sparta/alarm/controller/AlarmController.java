package com.project.sparta.alarm.controller;

import com.project.sparta.alarm.dto.AlarmDto;
import com.project.sparta.alarm.dto.AlarmMessageDto;
import com.project.sparta.alarm.dto.AlarmRoomDto;
import com.project.sparta.alarm.dto.AlarmMap;
import com.project.sparta.alarm.service.AlarmService;
import com.project.sparta.chat.dto.ChatDto;
import com.project.sparta.chat.dto.ChatRoomDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.service.CommunityBoardServiceImpl;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AlarmController {

    private final AlarmService alarmService;

    private final SimpMessageSendingOperations template;

    private final CommunityBoardServiceImpl communityBoardService;

    // 1. 사용자 마다 각자의 방을 만든다. => 방이름을 유저 nickName
    @PostMapping("/alarm/createRoom")
    public void createRoom(@RequestParam("roomName") String name,
        @RequestParam("alarmType") String chatType) {

        AlarmRoomDto room = alarmService.createAlarmRoom(name, chatType);

        log.info("CREATE Chat Room [{}]", room);
    }

    // 화면단에서 게시글의 주인의 방을 구독하고 -> 메세지를 보냄
    @MessageMapping("/alarm/enterUser")
    public void enterUser(@Payload AlarmDto alarm, SimpMessageHeaderAccessor headerAccessor) {

        //채팅방에 유저 추가 및 UserUUID 반환
        String userUUID = alarmService.addUser(AlarmMap.getInstance().getAlarmRooms(),
            alarm.getRoomName(), alarm.getSender());

        //반환 결과를 socket session에 userUUID로 저장
        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomName", alarm.getRoomName());

        template.convertAndSend("/sub/alarm/room/" + alarm.getRoomName(), alarm);
    }

    // 그럼 해당 메세지를 받는 사용자는 로그인하고 나서 자신의 방에 있는 메세지를 받아 볼 수 있음
    @MessageMapping("/alarm/message")
    public void alarmSendMessage(@RequestParam Long boardId, @Payload AlarmDto alarm) {

        CommunityBoardOneResponseDto board = communityBoardService.getBoard(boardId);

//        LocalDateTime now = LocalDateTime.now();
//        AlarmMessageDto messageDto = AlarmMessageDto.builder()
//            .title(board.getTitle())
//            .writerNickName(board.getNickName())
//            .sendNickName(userDetail.getUser().getNickName())
//            .boardType("댓글")
//            .time(now)
//            .build();
//
//        alarmController.alarmSendMessage("1", messageDto);
//        template.convertAndSend("/sub/alarm/room/" + roomId, messageDto);
    }
}




