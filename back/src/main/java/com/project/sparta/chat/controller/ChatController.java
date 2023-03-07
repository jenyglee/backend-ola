package com.project.sparta.chat.controller;

import com.project.sparta.chat.dto.ChatDto;
import com.project.sparta.chat.dto.ChatRoomMap;
import com.project.sparta.chat.service.ChatServiceMain;
import com.project.sparta.chat.service.MsgChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Join;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Api(tags = {"채팅"})
@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final SimpMessageSendingOperations template;

    private final ChatServiceMain chatServiceMain;

    private final MsgChatService msgChatService;

    private final ChatRoomMap chatRoomMap;


    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDto chat, SimpMessageHeaderAccessor headerAccessor){

        //채팅방 유저 + 1
        chatServiceMain.plusUserCnt(chat.getRoomId());

        //채팅방에 유저 추가 및 UserUUID 반환
        String userUUID = msgChatService.addUser(chatRoomMap.getChatRooms(), chat.getRoomId(), chat.getSender());

        //반환 결과를 socket session에 userUUID로 저장
        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setMessage(chat.getSender() + "님이 입장하셨습니다.");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    // 해당 유저
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDto chat) {
        log.info("CHAT {}", chat);
        chat.setMessage(chat.getMessage());
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    // 유저 퇴장 시에는 EventListener 을 통해서 유저 퇴장을 확인
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("DisConnEvent {}", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // stomp 세션에 있던 uuid 와 roomId 를 확인해서 채팅방 유저 리스트와 room 에서 해당 유저를 삭제
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headAccessor {}", headerAccessor);

        // 채팅방 유저 -1
        chatServiceMain.minusUserCnt(roomId);

        // 채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
        String username = msgChatService.findUserNameByRoomIdAndUserUUID(chatRoomMap.getChatRooms(), roomId, userUUID);
        msgChatService.delUser(chatRoomMap.getChatRooms(), roomId, userUUID);

        if (username != null) {
            log.info("User Disconnected : " + username);

            // builder 어노테이션 활용
            ChatDto chat = ChatDto.builder()
                .type(ChatDto.MessageType.LEAVE)
                .sender(username)
                .message(username + " 님이 퇴장하셨습니다.")
                .build();

            template.convertAndSend("/sub/chat/room/" + roomId, chat);
        }
    }
    // 채팅에 참여한 유저 닉네임 중복 확인
    @ApiOperation(value = "채팅 참여유저 닉네임 중복 확인",response = Join.class)
    @GetMapping("/chat/duplicateName")
    @ResponseBody
    public String isDuplicateName(@RequestParam("roomId") String roomId, @RequestParam("username") String username) {

        // 유저 이름 확인
        String userName = msgChatService.isDuplicateName(chatRoomMap.getChatRooms(), roomId, username);
        log.info("동작확인 {}", userName);

        return userName;
    }
}
