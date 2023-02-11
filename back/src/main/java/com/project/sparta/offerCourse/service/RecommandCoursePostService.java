package com.project.sparta.offerCourse.service;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.offerCourse.dto.RequestRecommandCoursePostDto;
import com.project.sparta.offerCourse.dto.ResponseFindAllRecommandCouesePostDto;
import com.project.sparta.offerCourse.dto.ResponseOnePostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecommandCoursePostService {

    //코스 등록
    List<String> creatRecomandCoursePost(List<MultipartFile> imges, RequestRecommandCoursePostDto requestPostDto) throws IOException;

    //코스 수정
    List<String> modifyRecommandCoursePost(Long id, List<MultipartFile> imges, RequestRecommandCoursePostDto requestPostDto) throws IOException;

    //코스 삭제
    void deleteRecommandCoursePost(Long id);

    //단건 코스 조회
    ResponseOnePostDto oneSelectRecommandCoursePost(Long id);


    //전체 코스 조회
    PageResponseDto<List<ResponseFindAllRecommandCouesePostDto>> allRecommandCousePost(int offset, int limit);
}
