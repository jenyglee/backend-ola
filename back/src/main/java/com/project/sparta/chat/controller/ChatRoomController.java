package com.project.sparta.chat.controller;


import com.project.sparta.chat.dto.ChatRoom;
import com.project.sparta.chat.repository.ChatRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Api(tags = {"채팅 방 API"})
@Controller
@Slf4j
public class ChatRoomController {

    //ChatRepostiory Bean 가져오기
    @Autowired
    private ChatRepository chatRepository;

    //채팅 리스트 화면
    // /로 요청이 들어오면 전체 채팅룸 리스트를 담아서 return
    @ApiOperation(value = "채팅 리스트 화면",response = Join.class)
    @GetMapping("/chat")
    public String goChatRoom(Model model){
        model.addAttribute("list", chatRepository.findAllRoom());

        log.info("all chatList ", chatRepository.findAllRoom());
        return "roomlist";      //view 단 url 넣어줘야 함
    }

    //채팅방 생성
    //채팅방 생성 후 다시 / return
    @ApiOperation(value = "채팅방 생성",response = Join.class)
    @PostMapping("/chat/createRoom")
    public String createRoom(@RequestParam String name, RedirectAttributes rttr){
        ChatRoom room = chatRepository.createChatRoom(name);
        log.info("create chat room : ", room);
        rttr.addFlashAttribute("roomName", room);
        return "/";
    }

    //채팅방 입장 화면
    //파라미터로 넘어오는 roomId를 확인 후 해당 roomId를 기준으로
    //채팅방을 찾아서 클라이언트를 chatroom으로 보낸다.
    @ApiOperation(value = "채팅방 입장 화면",response = Join.class)
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId){
        log.info("roomId : ", roomId);
        model.addAttribute("room", chatRepository.findRoomById(roomId));
        return "chatroom";
    }
}