package com.project.sparta.recommendCourse.service;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.RequestRecommendCoursePostDto;
import com.project.sparta.recommendCourse.dto.ResponseFindAllRecommendCouesePostDto;
import com.project.sparta.recommendCourse.dto.ResponseOnePostDto;
import com.project.sparta.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecommendCoursePostService {

    //코스 등록

    List<String> creatRecomendCoursePost(List<MultipartFile> imges, RequestRecommendCoursePostDto requestPostDto, Long userId) throws IOException;

    //코스 수정
    List<String> modifyRecommendCoursePost(Long id, List<MultipartFile> imges, RequestRecommendCoursePostDto requestPostDto,Long userId) throws IOException;

    //코스 삭제
    void deleteRecommendCoursePost(Long id,Long userId);

    //단건 코스 조회
    ResponseOnePostDto oneSelectRecommendCoursePost(Long id);


    //전체 코스 조회
    PageResponseDto<List<ResponseFindAllRecommendCouesePostDto>> allRecommendCousePost(int offset, int limit);
}
