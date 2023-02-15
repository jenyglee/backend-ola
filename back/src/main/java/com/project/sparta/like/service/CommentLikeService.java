package com.project.sparta.like.service;

import com.project.sparta.user.entity.User;

public interface CommentLikeService {

    void likeComment(Long id, User user);
    void unLikeComment(Long id, User user);
}
