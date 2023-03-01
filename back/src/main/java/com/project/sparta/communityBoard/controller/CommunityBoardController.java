package com.project.sparta.communityBoard.controller;

import com.project.sparta.chat.dto.ChatRoomDto;
import com.project.sparta.chat.service.ChatServiceMain;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Join;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Api(tags = {"커뮤니티 보드 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
@Slf4j
public class CommunityBoardController {

    private final CommunityBoardService communityBoardService;

    private final ChatServiceMain chatServiceMain;

    //커뮤니티 작성
    @ApiOperation(value = "커뮤니티 작성/채팅방 생성", response = Join.class)
    @PostMapping("/communities")
    public ResponseEntity createCommunityBoard(
        @RequestBody CommunityBoardRequestDto communityBoardRequestDto
        , @AuthenticationPrincipal UserDetailsImpl userDetail,
        RedirectAttributes rttr) {

        CommunityBoard board = communityBoardService.createCommunityBoard(communityBoardRequestDto, userDetail.getUser());

        if (communityBoardRequestDto.getChatStatus().equals("Y")) {
            // 매개변수 : 방 이름, 방 인원수, 방 타입
            ChatRoomDto room;

            room = chatServiceMain.createChatRoom(board.getId(), communityBoardRequestDto.getTitle(),
                communityBoardRequestDto.getChatMemCnt(), userDetail.getUser().getNickName());

            log.info("create chat room : " + communityBoardRequestDto.getTitle());

            rttr.addFlashAttribute("roomName", room);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //커뮤니티 단건 조회
    @ApiOperation(value = "커뮤니티 단건 조회", response = Join.class)
    @GetMapping("/communities/{boardId}")
    public ResponseEntity getCommunityBoard(@PathVariable Long boardId,
        @RequestParam(defaultValue = "0") int commentPage,
        @RequestParam(defaultValue = "8") int commentSize,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        long start = System.currentTimeMillis();
        CommunityBoardOneResponseDto CommunityBoardOneResponseDto = communityBoardService.getCommunityBoard(
            boardId, commentPage, commentSize, userDetails.getUser().getNickName());
        long end = System.currentTimeMillis();
        System.out.println("No 캐시 쿼리 수행 시간 : ");
        System.out.print(end-start);
        System.out.print("ms");
        return new ResponseEntity<>(CommunityBoardOneResponseDto, HttpStatus.OK);
    }


    @ApiOperation(value = "캐시 커뮤니티 전체 조회", response = Join.class)
    @GetMapping("/communities/cache")
    public ResponseEntity getCacheAllCommunityBoard(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "8") int size,
        @RequestParam String title,
        @RequestParam String contents,
        @RequestParam String nickname
        //@RequestParam Long hashtagId
    ) {
        long start = System.currentTimeMillis();
        PageResponseDto<List<CommunityBoardAllResponseDto>> result = communityBoardService.getCacheAllCommunityBoard(
            page, size, title, contents, nickname);
        long end = System.currentTimeMillis();
        System.out.println("Yes 쿼리 수행 시간 : ");
        System.out.print(end-start);
        System.out.print("ms");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //커뮤니티 전체 조회
    @ApiOperation(value = "커뮤니티 전체 조회", response = Join.class)
    @GetMapping("/communities")
    public ResponseEntity getAllCommunityBoard(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "8") int size,
        @RequestParam String title,
        @RequestParam String contents,
        @RequestParam String nickname
        //@RequestParam Long hashtagId
    ) {
        long start = System.currentTimeMillis();
        PageResponseDto<List<CommunityBoardAllResponseDto>> result = communityBoardService.getAllCommunityBoard(
            page, size, title, contents, nickname);
        long end = System.currentTimeMillis();
        System.out.println("No 쿼리 수행 시간 : ");
        System.out.print(end-start);
        System.out.print("ms");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //커뮤니티 수정
    @ApiOperation(value = "커뮤니티 수정", response = Join.class)
    @PatchMapping("/communities/{boardId}")
    public ResponseEntity updateCommunityBoard(@PathVariable Long boardId,
        @RequestBody CommunityBoardRequestDto communityBoardRequestDto
        , @AuthenticationPrincipal UserDetailsImpl userDetail) { // TODO 작성자를 체크!!!
        communityBoardService.updateCommunityBoard(boardId, communityBoardRequestDto,
            userDetail.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //커뮤니티 삭제
    @ApiOperation(value = "커뮤니티 삭제", response = Join.class)
    @DeleteMapping("/communities/{boardId}")
    public ResponseEntity deleteCommunityBoard(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetail) {
        communityBoardService.deleteCommunityBoard(boardId, userDetail.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }
}
