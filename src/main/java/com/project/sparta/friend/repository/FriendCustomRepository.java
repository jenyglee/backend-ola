package com.project.sparta.friend.repository;


import com.project.sparta.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FriendCustomRepository {

    Page<User> serachFriend(String targetUserName, Pageable pageRequest);
}
