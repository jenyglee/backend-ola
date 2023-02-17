package com.project.sparta.chat.controller;


import com.project.sparta.chat.repository.ChatRepository;
import com.sun.xml.bind.v2.TODO;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@Slf4j
public class ChatController {

    //ChatRepostiory Bean 가져오기
     private ChatRepository chatRepository;

     //TODO 실시간채팅 API 제작
    //실시간 채팅
    @PostMapping("/chat")
    public void chat(){}

}