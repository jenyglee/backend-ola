package com.project.sparta.admin.controller;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"어드민 Recommend API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminRecommendCourseController {
    private final RecommendCourseBoardService recommendCourseBoardService;

    // 코스추천 전체 조회
    @GetMapping("/boards/recommends")
    public ResponseEntity getRecommendList(@RequestParam int page, @RequestParam int size){
        PageResponseDto<List<RecommendResponseDto>> result = recommendCourseBoardService.allRecommendCourseBoard(page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 코스추천 단건 조회
    @GetMapping("/boards/recommends/{boardId}")
    public ResponseEntity getRecommend(@PathVariable Long boardId){
        RecommendDetailResponseDto result = recommendCourseBoardService.oneSelectRecommendCourseBoard(boardId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 코스추천 수정
    @PatchMapping("/boards/recommends/{boardId}")
    public ResponseEntity updateRecommend(@PathVariable Long boardId, @RequestBody RecommendRequestDto requestDto){
        recommendCourseBoardService.adminModifyRecommendCourseBoard(boardId, requestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 코스추천 삭제
    @DeleteMapping("/boards/recommends/{boardId}")
    public void deleteRecommend(@PathVariable Long boardId){
        recommendCourseBoardService.adminDeleteRecommendCourseBoard(boardId);
    }
}
