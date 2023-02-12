package com.project.sparta.offerCourse.repository;

import com.project.sparta.offerCourse.entity.RecommandCourseImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommandCoursePostImgRepository extends JpaRepository<RecommandCourseImg,Long> {


    @Query("select m.id from RecommandCourseImg m where m.recommandCoursePost.Id=:id ")
    List<Long> findByRecommendCoursePostId(@Param("id")Long id );




}
