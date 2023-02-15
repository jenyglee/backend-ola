package com.project.sparta.recommendCourse.repository;

import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendCourseBoardImgRepository extends JpaRepository<RecommendCourseImg,Long> {


//    @Query("select m.id from RecommendCourseImg m where m.recommendCoursePost.Id=:id ")

    // @Query("select m.id from RecommendCourseImg m where m.recommendCourseBoard.Id=:id")
    // List<Long> findByRecommendCoursePostId(@Param("id")Long id );




}
