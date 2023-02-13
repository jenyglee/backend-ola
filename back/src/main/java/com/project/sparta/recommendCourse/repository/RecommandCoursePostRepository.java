package com.project.sparta.recommendCourse.repository;

import com.project.sparta.recommendCourse.entity.RecommendCoursePost;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;

public interface RecommandCoursePostRepository extends JpaRepository<RecommendCoursePost,Long> {

    Optional<RecommendCoursePost> findByIdAndUserId(Long id, Long userId);
}
