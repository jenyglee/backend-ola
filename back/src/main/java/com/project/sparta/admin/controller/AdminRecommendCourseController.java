package com.project.sparta.admin.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
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
    @ApiOperation(value = "어드민 코스 추천 전체 조회", response = Join.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", value = "글 페이지", required = true, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "size", value = "글 보여질 개수", required = true, dataType = "int", paramType = "query", defaultValue = "10", example = "10"),
        @ApiImplicitParam(name = "score", value = "별점", required = false, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "season", value = "추천 계절", required = false, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "altitude", value = "산 높이", required = false, dataType = "int", paramType = "query", defaultValue = "0", example = "0"),
        @ApiImplicitParam(name = "region", value = "산 있는 지역", required = false, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "orderByLike", value = "정렬 기준(likeDesc/boardIdDesc)", required = false, dataType = "String", paramType = "query", example = "boardIdDesc"),
    })
        @GetMapping("/boards/recommends")
    public ResponseEntity getRecommendList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(required = false, name = "score") int score,
        @RequestParam(required = false, name = "season") String season,
        @RequestParam(required = false, name = "altitude") int altitude,
        @RequestParam(required = false, name = "region") String region,
        @RequestParam(required = false, name = "orderByLike") String orderByLike) {
        PageResponseDto<List<RecommendResponseDto>> result = recommendCourseBoardService.allRecommendCourseBoard(
            page, size, score, season, altitude, region, orderByLike);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 코스추천 단건 조회
    @ApiOperation(value = "코스 추천 단건 조회", response = Join.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "코스추천 ID", required = true, dataType = "Long", paramType = "path"),
    })
    @GetMapping("/boards/recommends/{boardId}")
    public ResponseEntity getRecommend(@PathVariable Long boardId,
        @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        RecommendDetailResponseDto result = recommendCourseBoardService.oneSelectRecommendCourseBoard(
            boardId, userDetails.getUser().getNickName());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //
    // 코스추천 수정
    @ApiOperation(value = "코스 추천 수정", response = Join.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "코스추천 ID", required = true, dataType = "Long", paramType = "path"),
    })
    @PatchMapping("/boards/recommends/{boardId}")
    public ResponseEntity updateRecommend(@PathVariable Long boardId,
        @RequestBody RecommendRequestDto requestDto) {
        recommendCourseBoardService.adminRecommendBoardUpdate(boardId, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 코스추천 삭제
    @ApiOperation(value = "코스 추천 삭제", response = Join.class)
    @ApiImplicitParams({
        @ApiImplicitParam(name = "boardId", value = "코스추천 ID", required = true, dataType = "Long", paramType = "path"),
    })

    @DeleteMapping("/boards/recommends/{boardId}")
    public void deleteRecommend(@PathVariable Long boardId) {
        recommendCourseBoardService.adminRecommendBoardDelete(boardId);
    }
}
