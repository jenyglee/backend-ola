package com.project.sparta.user.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.GetMyBoardResponseDto;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.recommendCourse.dto.GetMyRecommendCourseResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.dto.InfoResponseDto;
import com.project.sparta.user.dto.UpgradeRequestDto;
import com.project.sparta.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"정보조회"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {

    private final UserService userService;
    private final CommunityBoardService communityBoardService;
    private final RecommendCourseBoardService recommendCourseBoardService;

    // 내 정보 조회
    @ApiOperation(value = "내 정보 조회", response = Join.class)
    @GetMapping("/me")
    public ResponseEntity getMyInfo(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        InfoResponseDto result = userService.getMyInfo(userDetails.getUser());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    //내가 쓴 코스추천 전체 조회
    @ApiOperation(value = "내가 쓴 코스추천 전체 조회", response = Join.class)
    @GetMapping("/me/boards/recommends")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "size", value = "보여줄 개수", required = true, dataType = "int", paramType = "query", defaultValue = "8", example = "8"),
    })
    public ResponseEntity getMyBoardAll(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PageResponseDto<List<RecommendResponseDto>>  getMyRecommendCourseBoard= recommendCourseBoardService.getMyRecommendCourseBoard(page, size, userDetails.getUser());
        return new ResponseEntity<>(getMyRecommendCourseBoard,HttpStatus.OK);
    }

    //내가 쓴 커뮤니티 전체 조회
    @ApiOperation(value = "내가 쓴 커뮤니티 전체 조회", response = Join.class)
    @GetMapping("/me/boards/communities")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "size", value = "보여줄 개수", required = true, dataType = "int", paramType = "query", defaultValue = "8", example = "8"),
    })
    public ResponseEntity getMyCommunityList(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PageResponseDto<List<CommunityBoardAllResponseDto>> result = communityBoardService.getMyCommunityBoard(
            page, size, userDetails.getUser());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //내가 만든 크루 전체 조회
    @ApiOperation(value = "내가 만든 크루 전체 조회", response = Join.class)
    @GetMapping("/me/boards/chat")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "페이지", required = true, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "size", value = "보여줄 개수", required = true, dataType = "int", paramType = "query", defaultValue = "8", example = "8"),
    })
    public ResponseEntity getMyChatBoardList(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PageResponseDto<List<CommunityBoardAllResponseDto>> result = communityBoardService.getMyChatBoardList(page, size, userDetails.getUser().getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // TODO 친구가 쓴 게시글도 조회할수 있다면?
}
