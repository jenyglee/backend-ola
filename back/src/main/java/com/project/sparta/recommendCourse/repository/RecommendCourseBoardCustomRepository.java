package com.project.sparta.recommendCourse.repository;


import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.entity.PostStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RecommendCourseBoardCustomRepository {

    Page<RecommendResponseDto> allRecommendBoardList(PageRequest pageRequest, PostStatusEnum postStatusEnum, int score, String season, int altitude, String local, String orderByLike);

    RecommendDetailResponseDto getCourseBoard(Long boardId, PostStatusEnum postStatusEnum);
    Page<RecommendResponseDto>  myRecommendBoardList(PageRequest pageRequest, PostStatusEnum postStatusEnum, Long userId);
}
