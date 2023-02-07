package com.project.sparta.friend.service;

import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.friend.repository.FriendRepositoryImpl;
import com.project.sparta.user.dto.UserResponseDto;
import com.project.sparta.user.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final UserRepositoryImpl userRepository;


    @Override
    public void addFriend(Long userId, String targetUsername) {

        //친구 추가
        UserResponseDto targetUser = userRepository.findByUsername(targetUsername);

        if(targetUser.equals(null)){
            throw new IllegalArgumentException("회원정보를 찾을 수 없습니다.");
        }
        friendRepository.saveAndFlush(new Friend(userId, targetUser.getId()));
    }
}
