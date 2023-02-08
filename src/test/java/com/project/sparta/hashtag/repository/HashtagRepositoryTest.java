package com.project.sparta.hashtag.repository;

import com.project.sparta.exception.CustomException;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.service.HashtagService;
import com.project.sparta.user.entity.Tag;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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


        User user = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg",USER_REGISTERED, tags);

        // than
        // 내가 예상한 익센셥이 잘 나왔으면 테스트 성공
        assertThrows(CustomException.class, ()-> hashtagService.createHashtag("", user));
    }
    @Test
    @DisplayName("중복된 이름이 있는경우 에러")
    public void createHashTagError02(){
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

        hashtagRepository.save(new Hashtag("야호"));

        User user = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg",USER_REGISTERED, tags);

        // than
        assertThrows(CustomException.class, ()-> hashtagService.createHashtag("야호", user));
    }
}