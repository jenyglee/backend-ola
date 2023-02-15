package com.project.sparta.like.service;

import com.project.sparta.user.entity.User;

public interface RecommendCourseLikeService {

    void likeRecommendCourse(Long id, User user);
    void unLikeRecommendCourse(Long id, User user);
}
