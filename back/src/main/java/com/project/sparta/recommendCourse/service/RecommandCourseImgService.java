package com.project.sparta.recommendCourse.service;


import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecommandCourseImgService {

    //받은 파일 이미지리스트로 변환
    List<RecommendCourseImg> createImgList(List<MultipartFile> MultipartFile) throws IOException;

    void deleteImgList(Long id);
}
