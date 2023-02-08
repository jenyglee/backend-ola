package com.project.sparta.friend.service.service;

import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.friend.service.FriendService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendServiceImplTest {

    @Autowired
    FriendService friendService;

    @Autowired
    FriendRepository friendRepository;

    @Test
    public void addFriend(){
        User user1 = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg","Y");
        User user2 = new User("1234", "한세인", 20, "010-1234-1234", "user2@naver.com", UserRoleEnum.USER, "user2.jpg","Y");
        User user3 = new User("1234", "김민선", 30, "010-1234-1234", "user3@naver.com", UserRoleEnum.USER, "user3.jpg","Y");
        User user4 = new User("1234", "김주성", 20, "010-1234-1234", "user4@naver.com", UserRoleEnum.USER, "user4.jpg","Y");
        User user5 = new User("1234", "김두영", 30, "010-1234-1234", "user5@naver.com", UserRoleEnum.USER, "user5.jpg","Y");

        friendService.addFriend(1L, "김민선");

       /// Assertions.assertThat(user.get(0).getUserName()).isEqualTo("");
    }

}