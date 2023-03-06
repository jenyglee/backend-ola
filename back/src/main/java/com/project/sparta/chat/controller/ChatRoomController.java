package com.project.sparta.chat.controller;

import com.project.sparta.chat.dto.ChatRequestDto;
import com.project.sparta.chat.dto.ChatRoomDto;
import com.project.sparta.chat.dto.ChatRoomMap;
import com.project.sparta.chat.service.ChatServiceMain;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
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

    private final BoardRepository boardRepository;

    //채팅룸 생성(수정에서 사용되는 채팅 생성임)
    @PostMapping("/chat/room")
    public ResponseEntity roomDetail(@RequestBody ChatRequestDto chatRequestDto, @AuthenticationPrincipal
        UserDetailsImpl userDetails) {

        ChatRoomDto room;

        //1. 채팅방 처음 만들때는 N -> Y (채팅방 새로 만들어야 함)
        //2. 채팅방 Y -> N으로 변경할 때는 채팅방 수정해야함
        CommunityBoard board = boardRepository.findById(chatRequestDto.getRoomId()).orElseThrow(()-> new CustomException(
            Status.NOT_FOUND_POST));

        //아예 채팅방 만든 기록이 없을 경우
        if(board.getChatStatus().equals("L")){
            room = chatServiceMain.createChatRoom(chatRequestDto.getRoomId(), chatRequestDto.getTitle(),
                chatRequestDto.getChatMemCnt(), userDetails.getUser().getNickName());
            System.out.println(room.getRoomName() + "아이아어이ㅏㅇ");
        }
        else{
            updateChatRoom(chatRequestDto.getRoomId(), chatRequestDto.getChatMemCnt());
        }
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
    public void updateChatRoom(Long roomId, int roomMaxCnt) {

        ChatRoomDto beforeRoom = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        ChatRoomDto room = ChatRoomDto.builder()
            .roomId(beforeRoom.getRoomId())
            .roomName(beforeRoom.getRoomName())
            .hostName(beforeRoom.getHostName())
            .userCount(beforeRoom.getUserCount())
            .maxUserCnt(roomMaxCnt)
            .build();

        System.out.println("채팅방 수정 되나요?");

        ChatRoomMap.getInstance().getChatRooms().put(String.valueOf(roomId), room);
    }
}
