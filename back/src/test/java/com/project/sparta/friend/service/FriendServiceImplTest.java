package com.project.sparta.friend.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.dto.FriendInfoReponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.hashtag.service.HashtagServiceImpl;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserTag;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.service.UserService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class FriendServiceImplTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private HashtagRepository hashtagRepository;

    @Test
    @Transactional
    @DisplayName(value = "친구 추가")
    public void addFriend() {

        String randomUser1 = "user" + UUID.randomUUID();
        String randomUser2 = "user" + UUID.randomUUID();

        //user 생성
        User user1 = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        //친구 생성
        User user2 = User.userBuilder()
            .email(randomUser2)
            .password("user1234!")
            .nickName("내일은등산왕")
            .age(20)
            .phoneNumber("010-1234-1234")
            .build();

        User user = userRepository.save(user1);
        User friend = userRepository.save(user2);

        friendService.addFriend(user.getId(), friend.getId());
        List<Friend> friedList = friendRepository.findByUserId(user.getId());

        Assertions.assertThat(friedList.get(0).getTargetId()).isEqualTo(friend.getId());
    }

    @Test
    @Transactional
    @DisplayName(value = "친구 검색")
    public void searchFriend() {

        String randomUser1 = "user" + UUID.randomUUID();
        String randomUser2 = "user" + UUID.randomUUID();
        String uniqueNickName = "user" + UUID.randomUUID();

        //user 생성
        User user1 = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        //친구 생성
        User user2 = User.userBuilder()
            .email(randomUser2)
            .password("user1234!")
            .nickName(uniqueNickName)
            .age(20)
            .phoneNumber("010-1234-1234")
            .build();

        User user = userRepository.save(user1);
        User friend = userRepository.save(user2);

        friendService.addFriend(user.getId(), friend.getId());

        PageResponseDto result = friendService.searchFriend(0, 5, friend.getNickName());
        Assertions.assertThat(result.getTotalCount()).isEqualTo(1);
    }

    @Test
    @Transactional
    @DisplayName(value = "내 친구 전체조회")
    void allMyFriendList() {
        //내 친구 목록 전체 조회
        String randomUser1 = "user" + UUID.randomUUID();
        String randomUser2 = "user" + UUID.randomUUID();
        String randomUser3 = "user" + UUID.randomUUID();

        //user 생성
        User user1 = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        //친구 생성
        User user2 = User.userBuilder()
            .email(randomUser2)
            .password("user1234!")
            .nickName("내일은등산왕")
            .age(20)
            .phoneNumber("010-1234-1234")
            .build();

        //친구 생성
        User user3 = User.userBuilder()
            .email(randomUser3)
            .password("user1234!")
            .nickName("등린이탈출하자")
            .age(27)
            .phoneNumber("010-1234-1237")
            .build();

        User u1 = userRepository.save(user1);
        User u2 = userRepository.save(user2);
        User u3 = userRepository.save(user3);

        friendService.addFriend(u1.getId(), u2.getId());
        friendService.addFriend(u1.getId(), u3.getId());

        PageResponseDto result = friendService.AllMyFriendList(0, 5, u1.getId());
        Assertions.assertThat(result.getTotalCount()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName(value = "랜덤 친구 추천")
    void allRecommendFriendList() {

        String randomUser1 = "user" + UUID.randomUUID();
        String randomUser2 = "user" + UUID.randomUUID();
        String randomUser3 = "user" + UUID.randomUUID();

        //user 생성
        User user1 = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        //친구 생성
        User user2 = User.userBuilder()
            .email(randomUser2)
            .password("user1234!")
            .nickName("내일은등산왕")
            .age(20)
            .phoneNumber("010-1234-1234")
            .build();

        //친구 생성
        User user3 = User.userBuilder()
            .email(randomUser3)
            .password("user1234!")
            .nickName("등린이탈출하자")
            .age(27)
            .phoneNumber("010-1234-1237")
            .build();

        User u1 = userRepository.save(user1);
        User u2 = userRepository.save(user2);
        User u3 = userRepository.save(user3);

        List<UserTag> userTagList1 = new ArrayList<>();
        List<UserTag> userTagList2 = new ArrayList<>();
        List<UserTag> userTagList3 = new ArrayList<>();

        Hashtag hashtag = hashtagRepository.save(new Hashtag("올라1기"));

        userTagList1.add(new UserTag(u1, hashtag));
        userTagList2.add(new UserTag(u2, hashtag));
        userTagList3.add(new UserTag(u3, hashtag));

        u1.updateUserTags(userTagList1);
        u2.updateUserTags(userTagList2);
        u3.updateUserTags(userTagList3);

        PageResponseDto<List<FriendInfoReponseDto>> result = friendService.AllRecommendFriendList(0,
            5, u1.getId());
        Assertions.assertThat(result.getTotalCount()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName(value = "친구 삭제")
    public void deleteFriend() {

        String randomUser1 = "user" + UUID.randomUUID();
        String randomUser2 = "user" + UUID.randomUUID();

        //user 생성
        User user1 = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        //친구 생성
        User user2 = User.userBuilder()
            .email(randomUser2)
            .password("user1234!")
            .nickName("내일은등산왕")
            .age(20)
            .phoneNumber("010-1234-1234")
            .build();

        User u1 = userRepository.save(user1);
        User u2 = userRepository.save(user2);

        friendService.addFriend(u1.getId(), u2.getId());

        friendService.deleteFriend(u1.getId(), u2.getId());
        List<Friend> friend = friendRepository.findByUserId(u1.getId());

        Assertions.assertThat(friend.size()).isEqualTo(0);
    }
}