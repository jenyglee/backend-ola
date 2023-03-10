package com.project.sparta.friend.repository;

import com.project.sparta.exception.CustomException;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.service.FriendService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FriendRepositoryTest {

     @Autowired
     FriendService friendService;

     @Autowired
     FriendRepository friendRepository;

     @Autowired
     UserRepository userRepository;

     @Test
     @DisplayName("중복된 친구 추가")
     @Transactional
     public void duplicationFriend(){
         ArrayList<Long> member = addUser();
         friendRepository.saveAndFlush(new Friend(member.get(0), member.get(1)));
         assertThrows(CustomException.class, ()-> friendService.addFriend(member.get(0), member.get(1)));
     }
     @Test
     @DisplayName("회원 정보를 찾을 수 없는 경우")
     @Transactional
     public void notFoundUser(){
          ArrayList<Long> member = addUser();
          friendService.addFriend(member.get(0), member.get(1));

          assertThrows(CustomException.class, ()-> friendService.deleteFriend(member.get(0), 123456789100L));
          assertThrows(CustomException.class, ()-> friendService.AllRecommendFriendList(0, 5, 123456789100L));
     }

     @Test
     @Transactional
     @DisplayName(value = "내 친구의 회원정보를 찾을 수 없는 경우")
     void notFoundMyFriendInfo() {
          ArrayList<Long> member = addUser();
          friendService.addFriend(member.get(0), 123456789100L);

          assertThrows(CustomException.class, ()-> friendService.AllMyFriendList(0, 5, member.get(0)));
     }


     @Transactional
     public ArrayList addUser(){

          ArrayList<Long> membersId = new ArrayList<>();
          String randomUser1 = "user" + UUID.randomUUID();
          String randomUser2 = "user" + UUID.randomUUID();
          String uniqueNickName = "user" + UUID.randomUUID();

          //user 생성
          User user = User.userBuilder()
              .email(randomUser1)
              .password("user1234!")
              .nickName("내일은매니아")
              .age(25)
              .phoneNumber("010-1234-1235")
              .build();

          //친구 생성
          User friend = User.userBuilder()
              .email(randomUser2)
              .password("user1234!")
              .nickName(uniqueNickName)
              .age(20)
              .phoneNumber("010-1234-1234")
              .build();

          User user1 = userRepository.save(user);
          User friend1 = userRepository.save(friend);

          membersId.add(user1.getId());
          membersId.add(friend1.getId());

          return membersId;
     }
}