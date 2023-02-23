package com.project.sparta.recommendCourse.repository;

import com.project.sparta.recommendCourse.entity.RecommendCourseThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendCourseThumbnailRepository extends JpaRepository<RecommendCourseThumbnail, Long> {

    void deleteByRecommendCourseBoardId(Long boardId);
}
