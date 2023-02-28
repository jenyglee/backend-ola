package com.project.sparta.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sparta.security.UserDetailsImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Log4j2
public class WebSocketHandler extends TextWebSocketHandler {

    private static List<String> onlineList = new ArrayList<>();
    private static List<WebSocketSession> sessionList = new ArrayList<>();
    Map<String, WebSocketSession> userSession = new HashMap<>();

    ObjectMapper json = new ObjectMapper();


    // websocket을 통해서 받은 메세지를 처리하는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        //json test
       Map<String, Objects> dataMap = new HashMap<>();

       String masterStatus = null;
       if(userSession.containsKey("master")){
           masterStatus = "online";
       }else{
           masterStatus = "offline";
       }

        //메세지 보낸 시간
        LocalDateTime currentTime = LocalDateTime.now();
        String time = currentTime.format(DateTimeFormatter.ofPattern("hh:mm a, E"));

        //메세지 내용
        String senderId = (String) session.getAttributes().get("sessionId");
        String payload = message.getPayload();


        System.out.println("payLoad >>> " + payload);

//        dataMap = jsonToMap(payload);
//        dataMap.put("senderId", senderId);


    }


    // websocket에 session이 접속했을 때, 처리하는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    // websocket에 session이 접속을 해제 했을 때, 처리하는 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    }
}