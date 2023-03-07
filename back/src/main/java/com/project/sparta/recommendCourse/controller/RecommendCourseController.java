package com.project.sparta.recommendCourse.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.*;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import com.project.sparta.recommendCourse.service.RecommendCourseImgService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import io.swagger.annotations.*;
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
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"코스추천"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class RecommendCourseController {

    private final RecommendCourseBoardService recommendCourseBoardService;


    @ApiOperation(value = "코스 추천 생성", response = Join.class)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_GOD')")
    @PostMapping("/recommends")
    public ResponseEntity createRecommendCourse(@RequestBody @ApiParam(value = "코스추천 작성 값", required = true) RecommendRequestDto requestDto,
                                                @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetail) {

        Long userId = userDetail.getUser().getId();
        recommendCourseBoardService.creatRecommendCourseBoard(requestDto, userId);

        return new ResponseEntity(HttpStatus.OK);
    }


    @ApiOperation(value = "코스 추천 수정", response = Join.class)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_GOD')")
    @PatchMapping("/recommends/{boardId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "코스추천 ID", required = true, dataType = "Long", paramType = "path"),
    })
    public ResponseEntity modifyRecommendCourse(@RequestBody @ApiParam(value = "코스추천 수정 값", required = true) RecommendRequestDto requestDto,
                                                @PathVariable Long boardId,
                                                @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetail) {

        User user = userDetail.getUser();
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId, requestDto, user.getId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "코스 추천 삭제", response = Join.class)
    @PreAuthorize("hasRole('ROLE_USER:GRADE_GOD')")  //TODO @PreAuthorize에 함수를 사용하여 게시물작성자가 나인지 체크
    @DeleteMapping("/recommends/{boardId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "코스추천 ID", required = true, dataType = "Long", paramType = "path"),
    })
    public void deleteRecommendCourse(@PathVariable Long boardId,
                                      @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetail) {

        User user = userDetail.getUser();
        recommendCourseBoardService.deleteRecommendCourseBoard(boardId, user.getId());
    }

    @ApiOperation(value = "코스 추천 단건 조회", response = Join.class)
    @GetMapping("/recommends/{boardId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "코스추천 ID", required = true, dataType = "Long", paramType = "path"),
    })
    public RecommendDetailResponseDto oneRecommendCourse(@PathVariable Long boardId, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return recommendCourseBoardService.oneSelectRecommendCourseBoard(boardId, userDetails.getUser().getNickName());
    }

    @ApiOperation(value = "코스 추천 전체 조회", response = Join.class)
    @GetMapping("/recommends")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "글 페이지", required = true, dataType = "int", paramType = "query", defaultValue = "0" , example = "0"),
            @ApiImplicitParam(name = "size", value = "글 보여질 개수", required = true, dataType = "int", paramType = "query", defaultValue = "10", example = "10"),
            @ApiImplicitParam(name = "score", value = "별점", required = false, dataType = "int", paramType = "query", defaultValue = "0" , example = "0"),
            @ApiImplicitParam(name = "season", value = "추천 계절", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "altitude", value = "산 높이", required = false, dataType = "int", paramType = "query", defaultValue = "0" , example = "0"),
            @ApiImplicitParam(name = "region", value = "산 있는 지역", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "정렬 기준(likeDesc/boardIdDesc)", required = false, dataType = "String", paramType = "query", example = "boardIdDesc"),
    })
    public PageResponseDto<List<RecommendResponseDto>> allRecommendCourse(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(required = false, name = "score") int score,
        @RequestParam(required = false, name = "season") String season,
        @RequestParam(required = false, name = "altitude") int altitude,
        @RequestParam(required = false, name = "region") String region,
        @RequestParam(required = false, name = "sort") String sort) {
        return recommendCourseBoardService.allRecommendCourseBoard(page, size, score, season,
            altitude, region, sort);
    }
}
