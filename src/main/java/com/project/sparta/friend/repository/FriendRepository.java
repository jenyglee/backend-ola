package com.project.sparta.friend.repository;

import com.project.sparta.friend.entity.Friend;
import com.project.sparta.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FriendRepository extends JpaRepository<Friend, Long>, FriendCustomRepository{

    Friend findByUserIdAndTargetId(Long userId, Long targetId);

    List<Friend> findByUserId(Long userId);     //테스트용
}
