package com.project.sparta.friend.repository;

import com.project.sparta.friend.entity.Friend;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FriendRepository extends JpaRepository<Friend, Long>, FriendCustomRepository{

    Page<Friend> findAllByUserId(Long userId, Pageable pageable);

    Optional<Friend> findByUserIdAndTargetId(Long userId, Long targetId);

    Optional<Friend> findByTargetIdAndUserId(Long targetId ,Long userId);

    List<Friend> findByUserId(Long userId);     //테스트용

}
