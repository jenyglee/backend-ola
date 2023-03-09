package com.project.sparta.like.service;

import com.project.sparta.user.entity.User;

public interface RecommendCourseLikeService {

    Long likeRecommendCourse(Long id, User user);
    void unLikeRecommendCourse(Long id, User user);
}
