package com.project.sparta.chat.controller;

import static com.project.sparta.exception.api.Status.NOT_FOUND_COMMUNITY_BOARD;

import com.project.sparta.chat.dto.ChatRoomDto;
import com.project.sparta.chat.dto.ChatRoomMap;
import com.project.sparta.chat.service.ChatServiceMain;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.entity.CommunityBoardImg;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityBoard.repository.CommunityBoardImgRepository;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.security.UserDetailsImpl;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    // ChatService Bean 가져오기
    private final ChatServiceMain chatServiceMain;

    private final BoardRepository boardRepository;

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
