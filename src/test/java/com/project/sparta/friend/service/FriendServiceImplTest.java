package com.project.sparta.friend.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
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
        User user1 = new User("user1@naver.com","1234", "이재원", UserRoleEnum.USER, USER_REGISTERED, 10,"010-1234-1234","sdf.jpg",
            UserGradeEnum.MOUNTAIN_GOD);
          em.persist(user1);

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

        friendService.deleteFriend("한세인");
        List<Friend> friend = friendRepository.findByUserId(1L);

        Assertions.assertThat(friend.size()).isEqualTo(1);
    }

    @Test
    public void searchFriend(){
        PageResponseDto result = friendService.searchFriend(0, 5, "한세인");
        Assertions.assertThat(result.getTotalCount()).isEqualTo(1);
    }
}