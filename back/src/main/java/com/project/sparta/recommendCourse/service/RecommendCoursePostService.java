package com.project.sparta.recommendCourse.service;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecommendCoursePostService {

    //코스 등록

    List<String> creatRecommendCoursePost(List<MultipartFile> images, RecommendRequestDto requestPostDto, Long userId) throws IOException;

    //코스 수정
    List<String> modifyRecommendCoursePost(Long id, List<MultipartFile> images, RecommendRequestDto requestPostDto, Long userId) throws IOException;

    //코스 삭제
    void deleteRecommendCoursePost(Long id,Long userId);


    //단건 코스 조회
    RecommendDetailResponseDto oneSelectRecommendCoursePost(Long id);


    //전체 코스 조회
    PageResponseDto<List<RecommendResponseDto>> allRecommendCousePost(int offset, int limit);
}
