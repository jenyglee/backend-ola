package com.project.sparta.user.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.GetMyBoardResponseDto;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.recommendCourse.dto.GetMyRecommendCourseResponseDto;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.dto.UpgradeRequestDto;
import com.project.sparta.user.service.UserService;
import io.swagger.annotations.Api;
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
@Api(tags = {"정보조회 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {
    private final UserService userService;
    private final CommunityBoardService communityBoardService;
    private final RecommendCourseBoardService recommendCourseBoardService;

    // 내 정보 조회
    @ApiOperation(value = "내 정보 조회",response = Join.class)
    @GetMapping("/me")
    public ResponseEntity getMyInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.getMyInfo(userDetails.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //TODO 자동 등업 API 제작
    //자동 등업(명세서 수정)
    @ApiOperation(value = "자동 등업",response = Join.class)
    @PostMapping("/upgrade")
    public ResponseEntity autoUpgrade(UpgradeRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.upgrade(requestDto, userDetails.getUser().getId());
        return new ResponseEntity(HttpStatus.OK);
    }

    // TODO 내가 쓴 코스추천 전체 조회 API 제작
    //내가 쓴 코스추천 전체 조회
    @ApiOperation(value = "내가 쓴 코스추천 전체 조회",response = Join.class)
    @GetMapping("/me/boards/recommends")
    public ResponseEntity getMyBoardAll(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PageResponseDto<List<GetMyRecommendCourseResponseDto>>  getMyRecommendCourseBoard= recommendCourseBoardService.getMyRecommendCourseBoard(page, size, userDetails.getUser());
        return new ResponseEntity<>(getMyRecommendCourseBoard,HttpStatus.OK);
    }

    //내가 쓴 커뮤니티 전체 조회
    @ApiOperation(value = "내가 쓴 커뮤니티 전체 조회",response = Join.class)
    @GetMapping("/me/boards/communities")
    public ResponseEntity getMyCommunityList(
        @RequestParam("page") int page,
        @RequestParam("size") int size,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PageResponseDto<List<GetMyBoardResponseDto>> communityBoardResponseDto = communityBoardService.getMyCommunityBoard(page, size, userDetails.getUser());
        return new ResponseEntity<>(communityBoardResponseDto, HttpStatus.OK);
    }

    //TODO 내 알람 전체 조회 API 제작
    //내 알람 전체 조회
    @ApiOperation(value = "내 알람 전체 조회",response = Join.class)
    @GetMapping("/alarms")
    public void getAlarmList(){}

    // TODO 친구가 쓴 게시글도 조회할수 있다면?
}
