package com.project.sparta.chat.controller;

import com.project.sparta.chat.dto.ChatRoomDto;
import com.project.sparta.chat.dto.ChatRoomMap;
import com.project.sparta.chat.service.ChatServiceMain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    // ChatService Bean 가져오기
    private final ChatServiceMain chatServiceMain;

    @GetMapping("/chat/room")
    public ResponseEntity roomDetail(@RequestParam String roomId){

        log.info("roomId {}", roomId);

        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        return new ResponseEntity(room, HttpStatus.OK);
    }
    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId){

        return chatServiceMain.chkRoomUserCnt(roomId);
    }
    @GetMapping("/chat/delRoom/{roomId}")
    public ResponseEntity delChatRoom(@PathVariable String roomId){
        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        chatServiceMain.delChatRoom(roomId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
