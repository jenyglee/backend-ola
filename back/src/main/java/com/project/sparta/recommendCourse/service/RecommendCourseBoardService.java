package com.project.sparta.recommendCourse.service;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.GetMyRecommendCourseResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecommendCourseBoardService {

    //코스 등록

    void creatRecommendCourseBoard( RecommendRequestDto requestPostDto, Long userId);

    //코스 수정
    void modifyRecommendCourseBoard(Long id, RecommendRequestDto requestPostDto, Long userId);

    //코스 삭제
    void deleteRecommendCourseBoard(Long id, Long userId);

    //단건 코스 조회
    RecommendDetailResponseDto oneSelectRecommendCourseBoard(Long id);

    //전체 코스 조회
    PageResponseDto<List<RecommendResponseDto>> allRecommendCourseBoard(int offset, int limit);

    PageResponseDto<List<GetMyRecommendCourseResponseDto>> getMyRecommendCourseBoard(int page, int size, User user);
}
