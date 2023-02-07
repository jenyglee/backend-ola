package com.project.sparta.friend.repository;

import com.project.sparta.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long>, FriendCustomRepository{

}
