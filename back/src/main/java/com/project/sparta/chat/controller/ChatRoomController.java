package com.project.sparta.chat.controller;

import static com.project.sparta.exception.api.Status.NOT_FOUND_COMMUNITY_BOARD;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;
import com.project.sparta.chat.dto.ChatRoomDto;
import com.project.sparta.chat.dto.ChatRoomMap;
import com.project.sparta.chat.service.ChatServiceMain;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    // ChatService Bean 가져오기
    private final ChatServiceMain chatServiceMain;

    //채팅룸 상세 정보
    @GetMapping("/chat/room")
    public ResponseEntity roomDetail(@RequestParam String roomId) {

        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        // TODO CommunityBoardAllResponseDto에 현재 전체 참가자 수(maxUserCnt), 현재 참가자 수(userCount) 필드 추가
        // 1. 현재 참가자 수 추출
        int currentUser = room.getUserCount();

        //2. 해당 커뮤니티 추출
        CommunityBoard communityBoard = boardRepository.findById()
            .orElseThrow(() -> new CustomException(NOT_FOUND_COMMUNITY_BOARD));

        //3. 해당 커뮤니티에 참가자 수 입력
        communityBoard.updateCurrentMemCnt(currentUser);

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
            .roomId(beforeRoom.getRoomId())
            .roomName(beforeRoom.getRoomName())
            .hostName(beforeRoom.getHostName())
            .userCount(beforeRoom.getUserCount())
            .maxUserCnt(roomMaxCnt)
            .build();

        ChatRoomMap.getInstance().getChatRooms().put(roomId), room);

        return new ResponseEntity(HttpStatus.OK);
    }
}
