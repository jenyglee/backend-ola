package com.project.sparta.friend.repository;


import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FriendCustomRepository {

    PageImpl<User> randomUser(User userInfo, StatusEnum statusEnum, Pageable pageRequest);
    Page<User> serachFriend(String targetUserName, Pageable pageRequest);
}
