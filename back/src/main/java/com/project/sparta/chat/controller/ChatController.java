package com.project.sparta.chat.controller;


import com.project.sparta.chat.dto.ChatDTO;
import com.project.sparta.chat.dto.ChatDTO.MessageType;
import com.project.sparta.chat.repository.ChatRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.bind.annotation.PostMapping;

@Api(tags = {"채팅 유저 API"})
@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations template;

    @Autowired
    ChatRepository repository;



    //ChatRepostiory Bean 가져오기
    private ChatRepository chatRepository;

    //TODO 실시간채팅 API 제작
    //실시간 채팅
    @PostMapping("/chat")
    public void chat(){}

    //MessageMapping을 통해 webSocket으로 들어오는 메세지를 발신 처리 한다.
    // 이때 클라이언트에서는 /pub/chat/message로 요청하게 되고 이것을 controller가 받아서 처리 한다.
    // 처리가 완료되면 /sub/chat/room/roomId로 메세지가 전송된다.
    @ApiOperation(value = "유저입장",response = Join.class)
    @MessageMapping("/chat/enterUser")
    public void enterUser(@Payload ChatDTO chat, SimpMessageHeaderAccessor headerAccessor) {

        //채팅방 유저 +1
        repository.plusUserCnt(chat.getRoomId());

        //채팅방에 유저 추가 및 UserUUID 반환
        String userUUID = repository.addUser(chat.getRoomId(), chat.getSender());

        //반환 결과를 socket session에 userUUID로 저장
        headerAccessor.getSessionAttributes().put("userUUID", userUUID);
        headerAccessor.getSessionAttributes().put("roomId", chat.getRoomId());

        chat.setMessage(chat.getSender() + "님 입장");
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    //해당 유저
    @ApiOperation(value = "해당 유저 전송",response = Join.class)
    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatDTO chat) {
        log.info("CHAT : ", chat);
        chat.setMessage(chat.getMessage());
        template.convertAndSend("/sub/chat/room/" + chat.getRoomId(), chat);
    }

    //유저 퇴장 시에는 EventListener을 통해서 유저 퇴장을 확인
    @ApiOperation(value = "유저 퇴장을 확인",response = Join.class)
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("DisConnEven : ", event);

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        //stomp 세션에 있던 uuid와 roomId를 확인해서 채팅방 유저 리스트와 room에서 해당 유저를 삭제
        String userUUID = (String) headerAccessor.getSessionAttributes().get("userUUID");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        log.info("headerAccessor : ", headerAccessor);

        //채팅방 유저 -1
        repository.minusUserCnt(roomId);

        //채팅방 유저 리스트에서 UUID 유저 닉네임 조회 및 리스트에서 유저 삭제
        String username = repository.getUserName(roomId, userUUID);
        repository.delUser(roomId, userUUID);

        if (username != null) {
            log.info("User Disconnected : " + username);

            //builder 사용
            ChatDTO chat = ChatDTO.builder()
                .type(MessageType.LEAVE)
                .sender(username)
                .message(username + "님 퇴장")
                .build();

            template.convertAndSend("/sub/chat/room/" + roomId, chat);
        }
    }

    //채팅에 참여한 유저 리스트 반환
    @ApiOperation(value = "채팅에 참여한 유저 리스트",response = Join.class)
    @GetMapping("/chat/userlist")
    @ResponseBody
    public ArrayList<String> userList(String roomId){
        return repository.getUserList(roomId);
    }

    //채팅에 참여한 유저 닉네임 중복 확인
    @ApiOperation(value = "채팅에 참여한 유저 닉네임 중복 확인",response = Join.class)
    @GetMapping("/chat/duplicationName")
    @ResponseBody
    public String isDuplicatieName(@RequestParam("roomId") String roomId, @RequestParam("username") String username){
        String userName = repository.isDuplicateName(roomId, username);
        log.info("동작확인 : ", userName);

        return userName;
    }


}