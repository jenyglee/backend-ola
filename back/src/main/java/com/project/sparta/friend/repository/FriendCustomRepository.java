package com.project.sparta.friend.repository;


import com.project.sparta.friend.entity.Friend;
import com.project.sparta.user.entity.StatusEnum;
import com.project.sparta.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FriendCustomRepository {

    Page<User> randomUser(User userInfo, Pageable pageRequest, StatusEnum statusEnum);
    Page<User> serachFriend(String targetUserName, Pageable pageRequest);
}
