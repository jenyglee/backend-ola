package com.project.sparta.offerCourse.service;


import com.project.sparta.offerCourse.dto.RequestOfferCoursePostDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OfferCoursePostService {

    //코스 등록
    void creatOfferCoursePost(List<MultipartFile> imges, RequestOfferCoursePostDto requestPostDto) throws IOException;

    //코스 수정
    void modifyOfferCoursePost();

    //코스 삭제
    void deleteOfferCoursePost();

    //전체 코스 조회
    //단건 코스 조회
}
