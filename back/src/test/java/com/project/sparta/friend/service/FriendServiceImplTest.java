package com.project.sparta.friend.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.friend.dto.FriendInfoReponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.user.entity.*;
import com.project.sparta.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;
import static com.project.sparta.exception.api.Status.INVALID_USER;

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
    FriendRepository friendRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  HashtagRepository hashtagRepository;


    @BeforeEach
    public void Befor() {
        Hashtag tag1 = new Hashtag("등린이");
        Hashtag tag2 = new Hashtag("절경");
        Hashtag tag3 = new Hashtag("등산용품");
        Hashtag tag4 = new Hashtag("산악회");
        Hashtag tag5 = new Hashtag("정복");
        Hashtag tag6 = new Hashtag("고급코스");
        Hashtag tag7 = new Hashtag("짧은코스");
        Hashtag tag8 = new Hashtag("휴양등산");
        Hashtag tag9 = new Hashtag("예술풍경");
        Hashtag tag10 = new Hashtag("20대인기");

        em.persist(tag1);
        em.persist(tag2);
        em.persist(tag3);
        em.persist(tag4);
        em.persist(tag5);
        em.persist(tag6);
        em.persist(tag7);
        em.persist(tag8);
        em.persist(tag9);
        em.persist(tag10);


        List<Hashtag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);
        tagList.add(tag4);
        tagList.add(tag5);

        List<Hashtag> tagList2 = new ArrayList<>();
        tagList2.add(tag1);
        tagList2.add(tag6);
        tagList2.add(tag7);
        tagList2.add(tag8);
        tagList2.add(tag9);

        User user1 = new User("user1@naver.com","1234", "이재원", 10, "010-1234-1235","sdf.jpg");
        User user2 = new User("user2@naver.com","1234", "한세인", 30, "010-1234-1236","sdf.jpg");
        User user3 = new User("user3@naver.com","1234", "김민선", 30, "010-1234-1237","sdf.jpg");
        User user4 = new User("user4@naver.com","1234", "김주성", 20, "010-1234-1238","sdf.jpg");
        User user5 = new User("user5@naver.com","1234", "김두영", 20, "010-1234-1239","sdf.jpg");
        User user6 = new User("user6@naver.com","1234", "한병두", 30, "010-1234-1210","sdf.jpg");
        User user7 = new User("user7@naver.com","1234", "한지민", 40, "010-1234-1211","sdf.jpg");
        User user8 = new User("user8@naver.com","1234", "한예슬", 20, "010-1234-1212","sdf.jpg");
        User user9 = new User("user9@naver.com","1234", "한효주", 10, "010-1234-1213","sdf.jpg");
        User user10 = new User("user10@naver.com","1234", "조정석", 30, "010-1234-1214","sdf.jpg");


        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        em.persist(user5);
        em.persist(user6);
        em.persist(user7);
        em.persist(user8);
        em.persist(user9);
        em.persist(user10);

        UserTag userTag1 = new UserTag(user1, tag1);
        UserTag userTag2 = new UserTag(user1, tag2);
        UserTag userTag3 = new UserTag(user1, tag3);
        UserTag userTag4 = new UserTag(user1, tag4);
        UserTag userTag5 = new UserTag(user1, tag5);

        UserTag userTag6 = new UserTag(user2, tag1);
        UserTag userTag7 = new UserTag(user2, tag6);
        UserTag userTag8 = new UserTag(user2, tag7);
        UserTag userTag9 = new UserTag(user3, tag1);
        UserTag userTag10 = new UserTag(user3, tag8);

        UserTag userTag11 = new UserTag(user4, tag1);
        UserTag userTag12 = new UserTag(user4, tag6);
        UserTag userTag13 = new UserTag(user5, tag7);
        UserTag userTag14 = new UserTag(user5, tag1);
        UserTag userTag15 = new UserTag(user5, tag8);

        em.persist(userTag1);
        em.persist(userTag2);
        em.persist(userTag3);
        em.persist(userTag4);
        em.persist(userTag5);
        em.persist(userTag6);
        em.persist(userTag7);
        em.persist(userTag8);
        em.persist(userTag9);
        em.persist(userTag10);
        em.persist(userTag11);
        em.persist(userTag12);
        em.persist(userTag13);
        em.persist(userTag14);
        em.persist(userTag15);

        //초기화
        em.flush();
        em.clear();
    }

    @Test
    public void addFriend(){
        friendService.addFriend(1L, "한세인");
        friendService.addFriend(1L, "김민선");
        List<Friend> friend= friendRepository.findByUserId(1L);

        Assertions.assertThat(friend.get(0).getTargetId()).isEqualTo(2L);
        Assertions.assertThat(friend.get(1).getTargetId()).isEqualTo(3L);
    }


    @Test
    public void deleteFriend(){
        friendService.addFriend(1L, "한세인");
        friendService.addFriend(1L, "김민선");

        friendService.deleteFriend(2L);
        List<Friend> friend = friendRepository.findByUserId(1L);

        Assertions.assertThat(friend.size()).isEqualTo(1);
    }

    @Test
    public void searchFriend(){
        friendService.addFriend(1L, "한세인");
        friendService.addFriend(1L, "김민선");

        PageResponseDto result = friendService.searchFriend(0, 5, "한");
        Assertions.assertThat(result.getCurrentPage()).isEqualTo(0);
        Assertions.assertThat(result.getTotalCount()).isEqualTo(5);
    }

    @Test
    void allMyFriendList() {
        //내 친구 목록 전체 조회
        friendService.addFriend(1L, "한세인");
        friendService.addFriend(1L, "김민선");
        PageResponseDto result = friendService.AllMyFriendList(0, 5, 1L);
        Assertions.assertThat(result.getTotalCount()).isEqualTo(2);
    }

    @Test
    void allRecomentFriendList() {
        PageResponseDto<List<FriendInfoReponseDto>> result = friendService.AllRecomentFriendList(0, 5, 1L);
        Assertions.assertThat(result.getTotalCount()).isEqualTo(4);
    }
}