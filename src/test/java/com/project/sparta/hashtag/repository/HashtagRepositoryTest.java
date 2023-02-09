package com.project.sparta.hashtag.repository;

import com.project.sparta.exception.CustomException;
import com.project.sparta.hashtag.dto.HashtagRequestDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.service.HashtagService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HashtagRepositoryTest {
    @Autowired
    HashtagService hashtagService;
    @Autowired
    HashtagRepository hashtagRepository;


    @Test
    @DisplayName("빈 값 전달 에러")
    public void createHashTagError01(){
        User user1 = new User("user1@naver.com","1234", "이재원", UserRoleEnum.USER, USER_REGISTERED, 10,"010-1234-1234","sdf.jpg",
            UserGradeEnum.MOUNTAIN_GOD);

        assertThrows(CustomException.class, ()-> hashtagService.createHashtag("", user1));
    }

    @Test
    @DisplayName("중복된 이름이 있는경우 에러")
    public void createHashTagError02(){
        hashtagRepository.save(new Hashtag("야호"));

        User user1 = new User("user1@naver.com","1234", "이재원", UserRoleEnum.USER, USER_REGISTERED, 10,"010-1234-1234","sdf.jpg",
            UserGradeEnum.MOUNTAIN_GOD);
        assertThrows(CustomException.class, ()-> hashtagService.createHashtag("야호", user1));
    }
}