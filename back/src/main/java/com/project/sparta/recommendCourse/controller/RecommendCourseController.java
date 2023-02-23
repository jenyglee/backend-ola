package com.project.sparta.recommendCourse.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.*;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import com.project.sparta.recommendCourse.service.RecommendCourseImgService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags = {"코스추천 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class RecommendCourseController {

    private final RecommendCourseBoardService recommendCourseBoardService;


    @ApiOperation(value = "코스 추천 생성", response = Join.class)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_GOD')")
    @PostMapping("/recommends")
    public ResponseEntity createRecommendCourse(@RequestBody RecommendRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetail) {
        Long userId = userDetail.getUser().getId();
        recommendCourseBoardService.creatRecommendCourseBoard(requestDto, userId);

        return new ResponseEntity(HttpStatus.OK);
    }


    @ApiOperation(value = "코스 추천 수정", response = Join.class)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_GOD')")
    @PatchMapping("/recommends/{boardId}")
    public ResponseEntity modifyRecommendCourse(@RequestBody RecommendRequestDto requestDto,
        @PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetail) {

        User user = userDetail.getUser();
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId, requestDto, user.getId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "코스 추천 삭제", response = Join.class)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_GOD')")
    @DeleteMapping("/recommends/{boardId}")
    public void deleteRecommendCourse(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetail) {
        User user = userDetail.getUser();
        recommendCourseBoardService.deleteRecommendCourseBoard(boardId, user.getId());
    }

    @ApiOperation(value = "코스 추천 단건 조회", response = Join.class)
    @GetMapping("/recommends/{boardId}")
    public RecommendDetailResponseDto oneRecommendCourse(@PathVariable Long boardId) {
        return recommendCourseBoardService.oneSelectRecommendCourseBoard(boardId);
    }

    @ApiOperation(value = "코스 추천 전체 조회", response = Join.class)
    @GetMapping("/recommends")
    public PageResponseDto<List<RecommendResponseDto>> allRecommendCourse(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "score") int score,
        @RequestParam(name = "season") String season,
        @RequestParam(name = "altitude") int altitude,
        @RequestParam(name = "region") String region,
        @RequestParam(name = "orderByLike") String orderByLike) {
        return recommendCourseBoardService.allRecommendCourseBoard(page, size, score, season,
            altitude, region, orderByLike);
    }
}
