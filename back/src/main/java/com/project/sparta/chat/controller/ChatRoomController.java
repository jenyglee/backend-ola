package com.project.sparta.chat.controller;

import com.project.sparta.chat.dto.ChatRequestDto;
import com.project.sparta.chat.dto.ChatRoomDto;
import com.project.sparta.chat.dto.ChatRoomMap;
import com.project.sparta.chat.service.ChatServiceMain;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    // ChatService Bean 가져오기
    private final ChatServiceMain chatServiceMain;

    //채팅룸 생성(수정에서 사용되는 채팅 생성임)
    @PostMapping("/chat/room")
    public ResponseEntity roomDetail(@RequestBody ChatRequestDto chatRequestDto, @AuthenticationPrincipal
        UserDetailsImpl userDetails) {

        chatServiceMain.createChatRoom(chatRequestDto.getRoomId(), chatRequestDto.getTitle(),
                chatRequestDto.getChatMemCnt(), userDetails.getUser().getNickName());

        return new ResponseEntity(HttpStatus.OK);
    }


    //채팅룸 상세 정보
    @GetMapping("/chat/room")
    public ResponseEntity roomDetail(@RequestParam String roomId) {

        log.info("roomId {}", roomId);

        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        return new ResponseEntity(room, HttpStatus.OK);
    }

    //유저 카운트
    @GetMapping("/chat/chkUserCnt}")
    @ResponseBody
    public boolean chUserCnt(@RequestParam String roomId) {
        return chatServiceMain.chkRoomUserCnt(roomId);
    }

    //채팅룸 삭제
    @GetMapping("/chat/delRoom")
    public ResponseEntity delChatRoom(@RequestParam String roomId) {
        chatServiceMain.delChatRoom(roomId);
        return new ResponseEntity(HttpStatus.OK);
    }

    //채팅룸 수정
    @PostMapping("/chat/updateRoom")
    public ResponseEntity updateChatRoom(@RequestParam String roomId,
        @RequestParam int roomMaxCnt) {

        ChatRoomDto beforeRoom = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        ChatRoomDto room = ChatRoomDto.builder()
            .roomId(String.valueOf(beforeRoom.getRoomId()))
            .roomName(beforeRoom.getRoomName())
            .hostName(beforeRoom.getHostName())
            .userCount(beforeRoom.getUserCount())
            .maxUserCnt(roomMaxCnt)
            .build();

        ChatRoomMap.getInstance().getChatRooms().put(roomId, room);

        return new ResponseEntity(HttpStatus.OK);
    }
}
