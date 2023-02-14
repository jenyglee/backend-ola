package com.project.sparta.recommendCourse.repository;

import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendCourseBoardRepository extends JpaRepository<RecommendCourseBoard,Long> {

}
