package com.project.sparta.chat.dto;

import com.project.sparta.hashtag.entity.Hashtag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import lombok.Data;


//Stomp를 통해 pub/sub를 사용하면 구독자 관리가 알아서 된다.
//따라서 따로 세션 관리를 하는 코드를 작성할 필요가 없고,
//메세지를 다른 세션의 클라이언트에게 발송하는 것도 구현할 필요가 없다.
@Data
public class ChatRoom {

    private String roomId;      //채팅방 아이디
    private String roomName;    //채팅방 이름

    private List<Hashtag> tagList = new ArrayList<>();  //태그 리스트
    private long userCount;     //채팅방 인원수

    private HashMap<String, String> userlist = new HashMap<>();

    public ChatRoom create(String roomeName){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;

        return chatRoom;
    }
}
