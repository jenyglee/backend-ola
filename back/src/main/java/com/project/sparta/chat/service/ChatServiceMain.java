package com.project.sparta.chat.service;

import com.project.sparta.chat.dto.ChatRoomDto;
import com.project.sparta.chat.dto.ChatRoomMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class ChatServiceMain {

    private final MsgChatService msgChatService;

    private final ChatRoomMap chatRoomMap;

//    // 전체 채팅방 조회
//    public List<ChatRoomDto> findAllRoom(){
//        // 채팅방 생성 순서를 최근순으로 반환
//        List<ChatRoomDto> chatRooms = new ArrayList<>(ChatRoomMap.getInstance().getChatRooms().values());
//        Collections.reverse(chatRooms);
//
//        return chatRooms;
//    }
//
//    // roomID 기준으로 채팅방 찾기
//    public ChatRoomDto findRoomById(String roomId){
//        return ChatRoomMap.getInstance().getChatRooms().get(roomId);
//    }

    // roomName 로 채팅방 만들기
    public ChatRoomDto createChatRoom(Long chatId, String roomName, int maxUserCnt, String hostName){
        return msgChatService.createChatRoom(chatId, roomName, maxUserCnt, hostName);
    }

    // 채팅방 인원+1
    public void plusUserCnt(String roomId){
        ChatRoomDto room = chatRoomMap.getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount()+1);
    }

    // 채팅방 인원-1
    public void minusUserCnt(String roomId){
        ChatRoomDto room = chatRoomMap.getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount()-1);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(String roomId){
        ChatRoomDto room = chatRoomMap.getChatRooms().get(roomId);
        if (room.getUserCount() + 1 > room.getMaxUserCnt()) {
            return false;
        }
        return true;
    }
    // 채팅방 삭제
    public void delChatRoom(String roomId){
        try {
            chatRoomMap.getChatRooms().remove(roomId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
