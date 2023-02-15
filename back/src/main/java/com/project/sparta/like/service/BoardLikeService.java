package com.project.sparta.like.service;

import com.project.sparta.user.entity.User;

public interface BoardLikeService {

    void likeBoard(Long id, User user);
    void unLikeBoard(Long id, User user);
}
