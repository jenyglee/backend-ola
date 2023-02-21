package com.project.sparta.recommendCourse.repository;

import com.project.sparta.recommendCourse.entity.PostStatusEnum;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecommendCourseBoardRepository extends JpaRepository<RecommendCourseBoard,Long>, RecommendCourseBoardCustomRepository {

    @Query("select rb from RecommendCourseBoard rb where rb.postStatus =:postStatusEnum")
    Page<RecommendCourseBoard> findAllBySatusIsVailable(Pageable pageable, PostStatusEnum postStatusEnum);

    Page<RecommendCourseBoard> findByUserId(Pageable pageable, Long userId);

    Long countByUserId(Long userId);
}
