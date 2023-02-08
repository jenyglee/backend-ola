package com.project.sparta.friend.repository;

import com.project.sparta.friend.entity.Friend;
import com.project.sparta.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FriendRepository extends JpaRepository<Friend, Long>, FriendCustomRepository{

    Page<User> findUserByUsernameStartWith(String targetUsername, Pageable pageRequest);

    List<User> findByUserId(Long userId);
}
