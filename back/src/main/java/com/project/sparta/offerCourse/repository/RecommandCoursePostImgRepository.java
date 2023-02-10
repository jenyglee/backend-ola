package com.project.sparta.offerCourse.repository;

import com.project.sparta.offerCourse.entity.RecommandCourseImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommandCoursePostImgRepository extends JpaRepository<RecommandCourseImg,Long> {

    List<RecommandCourseImg> findByRecommandCoursePost_Id(Long id);


}
