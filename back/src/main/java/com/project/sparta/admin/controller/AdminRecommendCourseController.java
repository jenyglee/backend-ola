package com.project.sparta.admin.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"어드민 코스추천"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminRecommendCourseController {
    private final RecommendCourseBoardService recommendCourseBoardService;

    // 코스추천 전체 조회
    @GetMapping("/boards/recommends")
    public ResponseEntity getRecommendList(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam int score,
        @RequestParam String season,
        @RequestParam int altitude,
        @RequestParam String region,
        @RequestParam String orderByLike) {
        PageResponseDto<List<RecommendResponseDto>> result = recommendCourseBoardService.allRecommendCourseBoard(
            page, size, score, season, altitude, region, orderByLike);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 코스추천 단건 조회
    @GetMapping("/boards/recommends/{boardId}")
    public ResponseEntity getRecommend(@PathVariable Long boardId,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        RecommendDetailResponseDto result = recommendCourseBoardService.oneSelectRecommendCourseBoard(
            boardId, userDetails.getUser().getNickName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //
    // 코스추천 수정
    @PatchMapping("/boards/recommends/{boardId}")
    public ResponseEntity updateRecommend(@PathVariable Long boardId,
        @RequestBody RecommendRequestDto requestDto) {
        recommendCourseBoardService.adminRecommendBoardUpdate(boardId, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 코스추천 삭제
    @DeleteMapping("/boards/recommends/{boardId}")
    public void deleteRecommend(@PathVariable Long boardId) {
        recommendCourseBoardService.adminRecommendBoardDelete(boardId);
    }
}
