package com.project.sparta.recommendCourse.service;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.GetMyRecommendCourseResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RecommendCourseBoardService {

    //코스 등록

    Long creatRecommendCourseBoard( RecommendRequestDto requestPostDto, Long userId);

    //코스 수정
    void modifyRecommendCourseBoard(Long id, RecommendRequestDto requestPostDto, Long userId);

    //코스 삭제
    void deleteRecommendCourseBoard(Long id, Long userId);

    //단건 코스 조회
    RecommendDetailResponseDto oneSelectRecommendCourseBoard(Long id, String nickName);

    //전체 코스 조회
    PageResponseDto<List<RecommendResponseDto>> allRecommendCourseBoard(int page, int size, int score, String season, int altitude, String region, String orderByLike);

    //내가 쓴 코스추천 전체 조회
    PageResponseDto<List<RecommendResponseDto>> getMyRecommendCourseBoard(int page, int size, User user);


    //어드민 코스 수정
    void adminRecommendBoardUpdate(Long id, RecommendRequestDto requestDto);

    //어드민 코스 삭제
    void adminRecommendBoardDelete(Long id);

}
