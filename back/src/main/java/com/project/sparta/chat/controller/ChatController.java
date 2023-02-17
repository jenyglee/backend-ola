package com.project.sparta.chat.controller;


import com.project.sparta.chat.repository.ChatRepository;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
public class ChatController {

    //ChatRepostiory Bean 가져오기
   private ChatRepository chatRepository;
}