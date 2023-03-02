package com.project.sparta.like.repository;

import com.project.sparta.like.entity.CourseLike;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import org.springframework.data.repository.query.Param;


public interface LikeRecommendRepository extends JpaRepository<CourseLike,Long> {

    @Query("select cl from CourseLike cl where cl.userEmail =:email and cl.courseBoard =:courseBoard")
    Optional<CourseLike> findByUserEmailAndCourseBoard(@Param("email") String email,@Param("courseBoard") RecommendCourseBoard courseBoard);


    Long countByCourseBoard_Id(Long id);

}
