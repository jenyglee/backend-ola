package com.project.sparta.hashtag.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.sparta.exception.CustomException;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.service.HashtagService;
import com.project.sparta.user.entity.User;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class HashtagRepositoryTest {
     // TODO 테스트코드 추가 : 해시태그 삭제 시 찾을 수 없는 경우(재원)
     @Autowired
     HashtagService hashtagService;
     @Autowired
     HashtagRepository hashtagRepository;

     @Test
     @DisplayName("빈 값 전달 에러")
     @Transactional
     public void emptyCondition(){
         User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
         assertThrows(CustomException.class, ()-> hashtagService.createHashtag("", user1));
     }

     @Test
     @DisplayName("중복된 이름이 있는경우 에러")
     @Transactional
     public void duplicationTagName(){

         hashtagRepository.save(new Hashtag("야호"));

         User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
         assertThrows(CustomException.class, ()-> hashtagService.createHashtag("야호", user1));
     }

    @Test
    @DisplayName("태그를 찾을 수 없는 경우")
    @Transactional
    public void notFoundTag(){
        hashtagRepository.save(new Hashtag("야호"));
        User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
        assertThrows(CustomException.class, ()-> hashtagService.deleteHashtag(99999999L, user1));
    }
}