package com.project.sparta.offerCourse.repository;

import com.project.sparta.offerCourse.entity.RecommandCoursePost;
import com.project.sparta.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommandCoursePostRepository extends JpaRepository<RecommandCoursePost,Long>, RecommendCoursePostCustomRepository {
    Long countByUser(User user);
}
