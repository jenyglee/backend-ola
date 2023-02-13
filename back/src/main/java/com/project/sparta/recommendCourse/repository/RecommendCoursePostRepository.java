package com.project.sparta.recommendCourse.repository;

import com.project.sparta.recommendCourse.entity.RecommendCoursePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendCoursePostRepository extends JpaRepository<RecommendCoursePost,Long> {

}
