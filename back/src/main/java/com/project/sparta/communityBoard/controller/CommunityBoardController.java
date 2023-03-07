package com.project.sparta.communityBoard.controller;

import com.project.sparta.chat.dto.ChatRoomDto;
import com.project.sparta.chat.service.ChatServiceMain;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardGradeResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"커뮤니티"})
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
        @RequestBody @ApiParam(value = "커뮤니티 작성 값", required = true) CommunityBoardRequestDto requestDto,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetail,RedirectAttributes rttr) {

        CommunityBoard board = communityBoardService.createCommunityBoard(requestDto, userDetail.getUser());
        CommunityBoardGradeResponseDto communityBoardGradeResponseDto = new CommunityBoardGradeResponseDto(board.getManiaResponse(),
            board.getGodResponse());
        if (requestDto.getChatStatus().equals("Y")) {
            // 매개변수 : 방 이름, 방 인원수, 방 타입
            ChatRoomDto room;

            room = chatServiceMain.createChatRoom(board.getId(), requestDto.getTitle(),
                requestDto.getChatMemCnt(), userDetail.getUser().getNickName());

            rttr.addFlashAttribute("roomName", room);
        }

        return new ResponseEntity<>(communityBoardGradeResponseDto,HttpStatus.OK);
    }

    //커뮤니티 단건 조회
    @ApiOperation(value = "커뮤니티 단건 조회", response = Join.class)
    @GetMapping("/communities/{boardId}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "커뮤니티 ID", required = true, dataType = "Long", paramType = "path", example = "123"),
        @ApiImplicitParam(name = "commentPage", value = "댓글 페이지", required = true, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "commentSize", value = "댓글 보여질 개수", required = true, dataType = "int", paramType = "query", example = "10")
    })
    public ResponseEntity getCommunityBoard(
        @PathVariable Long boardId,
        @RequestParam(defaultValue = "0") int commentPage,
        @RequestParam(defaultValue = "8") int commentSize,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        //long start = System.currentTimeMillis();
        CommunityBoardOneResponseDto result = communityBoardService.getCommunityBoard(
            boardId, commentPage, commentSize, userDetails.getUser().getNickName());
        //long end = System.currentTimeMillis();
        //System.out.println("No 캐시 쿼리 수행 시간 : ");
        //System.out.print(end-start);
        //System.out.print("ms");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //커뮤니티 전체 조회
    @ApiOperation(value = "커뮤니티 전체 조회", response = Join.class)
    @GetMapping("/communities")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "path", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "size", value = "보여줄 개수", required = true, dataType = "String", paramType = "query", defaultValue = "10", example = "10"),
        @ApiImplicitParam(name = "title", value = "제목", required = false, dataType = "String", paramType = "query", defaultValue = "0"),
        @ApiImplicitParam(name = "contents", value = "내용", required = false, dataType = "String", paramType = "query", defaultValue = "0"),
        @ApiImplicitParam(name = "nickname", value = "작성자명", required = false, dataType = "String", paramType = "query", defaultValue = "0"),
        @ApiImplicitParam(name = "hashtagId", value = "태그 ID", required = false, dataType = "Long", paramType = "query", example = "1"),
        @ApiImplicitParam(name = "chatStatus", value = "크루원모집 상태(Y/N)", required = false, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "sort", value = "정렬 기준(likeDesc/boardIdDesc)", required = false, dataType = "String", paramType = "query", defaultValue = "boardIdDesc"),
    })
    public ResponseEntity getAllCommunityBoard(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "8") int size,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String contents,
        @RequestParam(required = false) String nickname,
        @RequestParam(required = false) Long hashtagId,
        @RequestParam(required = false) String chatStatus,
        @RequestParam(required = false) String sort
    ) {
        //long start = System.currentTimeMillis();
        PageResponseDto<List<CommunityBoardAllResponseDto>> result = communityBoardService.getAllCommunityBoard(
            page, size, title, contents, nickname, hashtagId, chatStatus, sort);
        //long end = System.currentTimeMillis();
        //System.out.println("No 쿼리 수행 시간 : ");
        //System.out.print(end-start);
        //System.out.print("ms");

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //커뮤니티 수정
    @ApiOperation(value = "커뮤니티 수정", response = Join.class)
    @PatchMapping("/communities/{boardId}")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "커뮤니티 ID", required = true, dataType = "Long", paramType = "path", example = "123"),
    })
    public ResponseEntity updateCommunityBoard(
        @PathVariable Long boardId,
        @RequestBody @ApiParam(value = "커뮤니티 수정 값", required = true) CommunityBoardRequestDto requestDto,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetail) { // TODO 작성자를 체크!!!
        communityBoardService.updateCommunityBoard(boardId, requestDto,
            userDetail.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //커뮤니티 삭제
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "커뮤니티 ID", required = true, dataType = "Long", paramType = "path", example = "123"),
    })
    @ApiOperation(value = "커뮤니티 삭제", response = Join.class)
    @DeleteMapping("/communities/{boardId}")
    public ResponseEntity deleteCommunityBoard(@PathVariable Long boardId,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetail) {
        communityBoardService.deleteCommunityBoard(boardId, userDetail.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }
}
