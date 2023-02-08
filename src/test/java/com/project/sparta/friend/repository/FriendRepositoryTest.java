package com.project.sparta.friend.repository;

import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.service.FriendService;
import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendRepositoryTest {

    @Autowired
    FriendService friendService;

    @Autowired
    FriendRepository friendRepository;
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }


    @Test
    @DisplayName("회원정보를 찾을 수 없는 경우")
    public void deleteFriend(){
        assertThrows(IllegalArgumentException.class, ()-> friendService.deleteFriend(" "));
    }

    @Test
    @DisplayName("중복된 친구 추가")
    public void addFriend(){

        friendService.addFriend(1L, "한세인");
        assertThrows(DataIntegrityViolationException.class, ()-> friendService.addFriend(1L, "한세인"));
    }
}