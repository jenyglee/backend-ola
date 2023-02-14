package com.project.sparta.like.repository;

import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.like.entity.BoardLike;
import com.project.sparta.like.entity.CommentLike;
import com.project.sparta.like.entity.CourseLike;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LikeRecommendRepository extends JpaRepository<CourseLike,Long> {

    Optional<CourseLike> findByUserEmailAndCourseBoard(String email, RecommendCourseBoard courseBoard);


}
