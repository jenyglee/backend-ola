package com.project.sparta.hashtag.repository;

import com.project.sparta.hashtag.service.HashtagService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HashtagRepositoryTest {
    @Autowired
    HashtagService hashtagService;


    @Test
    @DisplayName("빈 값 전달 에러")
    public void createHashTagError01(){
        User user = new User("1234", "이재원", 20, "01010101", "wodnj@naver.com", UserRoleEnum.USER);

        // than
        // 내가 예상한 익센셥이 잘 나왔으면 테스트 성공
        assertThrows(IllegalArgumentException.class, ()-> hashtagService.createHashtag("", user));
    }
}