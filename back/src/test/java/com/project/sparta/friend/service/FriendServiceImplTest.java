package com.project.sparta.friend.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.friend.dto.FriendInfoReponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.service.UserService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FriendServiceImplTest {

     @Autowired
     EntityManager em;
     JPAQueryFactory queryFactory;
     @BeforeEach
     public void before() {
         queryFactory = new JPAQueryFactory(em);
     }

     @Autowired
     FriendService friendService;

     @Autowired
     UserService userService;

     @Autowired
     UserRepository userRepository;

//     @Test
//     public void addFriend(){
//
//         String randomUser = "user"+ UUID.randomUUID();
//
//         //user 생성
//         User user = User.userBuilder()
//             .email(randomUser)
//             .password("user1234!")
//             .nickName("내일은매니아")
//             .age(25)
//             .phoneNumber("010-1234-1235")
//             .build();
//
//
//         //친구 생성
//         User friend = User.userBuilder()
//             .email(randomUser)
//             .password("user1234!")
//             .nickName("내일은등산왕")
//             .age(20)
//             .phoneNumber("010-1234-1234")
//             .build();
//
//         User user1 = userRepository.save(user);
//         User friend1 = userRepository.save(friend);
//
//         friendService.addFriend(user1.getId(), friend1.getId());
//
//         Assertions.assertThat(friend.get(0).getTargetId()).isEqualTo(2L);
//         Assertions.assertThat(friend.get(1).getTargetId()).isEqualTo(3L);
//     }
//
//
//     @Test
//     public void deleteFriend(){
//         friendService.addFriend(1L, "한세인");
//         friendService.addFriend(1L, "김민선");
//
//         friendService.deleteFriend(2L);
//         List<Friend> friend = friendRepository.findByUserId(1L);
//
//         Assertions.assertThat(friend.size()).isEqualTo(1);
//     }
//
//     @Test
//     public void searchFriend(){
//         friendService.addFriend(1L, "한세인");
//         friendService.addFriend(1L, "김민선");
//
//         PageResponseDto result = friendService.searchFriend(0, 5, "한");
//         Assertions.assertThat(result.getCurrentPage()).isEqualTo(0);
//         Assertions.assertThat(result.getTotalCount()).isEqualTo(5);
//     }
//
//     @Test
//     void allMyFriendList() {
//         //내 친구 목록 전체 조회
//         friendService.addFriend(1L, "한세인");
//         friendService.addFriend(1L, "김민선");
//         PageResponseDto result = friendService.AllMyFriendList(0, 5, 1L);
//         Assertions.assertThat(result.getTotalCount()).isEqualTo(2);
//     }
//
//     @Test
//     void allRecomentFriendList() {
//         PageResponseDto<List<FriendInfoReponseDto>> result = friendService.AllRecomentFriendList(0, 5, 1L);
//         Assertions.assertThat(result.getTotalCount()).isEqualTo(4);
//     }
}