package com.project.sparta.friend.service;

import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.user.entity.Tag;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
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


    @BeforeEach
    public void Befor() {

        Tag tag1 = new Tag("#등린이");
        Tag tag2 = new Tag("#절경");
        Tag tag3 = new Tag("#등산용품");
        Tag tag4 = new Tag("#산악회");
        Tag tag5 = new Tag("#정복");
        Tag tag6 = new Tag("#고급코스");

        List<Tag> tags = new ArrayList<Tag>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        tags.add(tag5);
        tags.add(tag6);


        User user1 = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg",USER_REGISTERED, tags);
        User user2 = new User("1234", "한세인", 20, "010-1234-1235", "user2@naver.com", UserRoleEnum.USER, "user2.jpg",USER_REGISTERED, tags);
        User user3 = new User("1234", "김민선", 30, "010-1234-1236", "user3@naver.com", UserRoleEnum.USER, "user3.jpg",USER_REGISTERED, tags);
        User user4 = new User("1234", "김주성", 20, "010-1234-1237", "user4@naver.com", UserRoleEnum.USER, "user4.jpg",USER_REGISTERED, tags);
        User user5 = new User("1234", "김두영", 30, "010-1234-1238", "user5@naver.com", UserRoleEnum.USER, "user5.jpg",USER_REGISTERED, tags);
        User user6 = new User("1234", "한병두", 20, "010-1234-1239", "user6@naver.com", UserRoleEnum.USER, "user6.jpg",USER_REGISTERED, tags);
        User user7 = new User("1234", "한지민", 10, "010-1234-1222", "user7@naver.com", UserRoleEnum.USER, "user7.jpg",USER_REGISTERED, tags);
        User user8 = new User("1234", "한예슬", 20, "010-1234-1213", "user8@naver.com", UserRoleEnum.USER, "user8.jpg",USER_REGISTERED, tags);
        User user9 = new User("1234", "한예리", 30, "010-1234-1255", "user9@naver.com", UserRoleEnum.USER, "user9.jpg",USER_REGISTERED, tags);
        User user10 = new User("1234", "한호영", 20, "010-1234-1227", "user10@naver.com", UserRoleEnum.USER, "user10.jpg",USER_REGISTERED, tags);
        User user11 = new User("1234", "한민선", 30, "010-1234-1211", "user11@naver.com", UserRoleEnum.USER, "user11.jpg",USER_REGISTERED, tags);


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
        em.persist(user11);

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

    }

    @Test
    void allRecomentFriendList() {
       PageResponseDto result = friendService.AllRecomentFriendList(0, 3, 1l);
       Assertions.assertThat(result.getTotalCount()).isEqualTo(3);
    }
}