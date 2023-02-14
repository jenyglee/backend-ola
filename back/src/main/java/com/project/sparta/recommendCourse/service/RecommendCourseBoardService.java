package com.project.sparta.recommendCourse.service;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecommendCourseBoardService {

    //코스 등록

    List<String> creatRecommendCourseBoard(List<MultipartFile> images, RecommendRequestDto requestPostDto, Long userId) throws IOException;

    //코스 수정
    List<String> modifyRecommendCourseBoard(Long id, List<MultipartFile> images, RecommendRequestDto requestPostDto, Long userId) throws IOException;

    //코스 삭제
    void deleteRecommendCourseBoard(Long id, Long userId);

    //단건 코스 조회
    RecommendDetailResponseDto oneSelectRecommendCourseBoard(Long id);

    //전체 코스 조회
    PageResponseDto<List<RecommendResponseDto>> allRecommendCourseBoard(int offset, int limit);
}
