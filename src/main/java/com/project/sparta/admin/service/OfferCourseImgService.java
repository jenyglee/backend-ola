package com.project.sparta.admin.service;


import com.project.sparta.admin.entity.OfferCourseImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OfferCourseImgService {

    //받은 파일 이미지리스트로 변환
    List<OfferCourseImg> createImgList(List<MultipartFile> MultipartFile) throws IOException;

    //코스 삭제
    void deleteOfferCourseImg();

}
