package com.project.sparta.hashtag.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class HashtagServiceImplTest {

    @Autowired HashtagService hashtagService;
    @Autowired
    HashtagRepository hashtagRepository; // 방금추가
    @Test
    public void createHashtag() {
        User user = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg","Y");

        Hashtag saved = hashtagService.createHashtag("등린이", user);

        assertThat(saved.getName()).isEqualTo("등린이");
    }

    @Test
    public void deleteHashtag() {
        User user = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg","Y");
        Hashtag saved = hashtagRepository.save(new Hashtag("등린이"));

        hashtagService.deleteHashtag(saved.getId(), user);

        List<Hashtag> allHashtag = hashtagRepository.findAll();
        assertThat(allHashtag.size()).isEqualTo(0);
        assertThat(saved.getName()).isEqualTo("등린이");
    }

    @Test
    public void getHashtagList(){
        hashtagRepository.save(new Hashtag("등린이"));
        hashtagRepository.save(new Hashtag("금강산"));
        hashtagRepository.save(new Hashtag("식후경"));
        hashtagRepository.save(new Hashtag("야호"));

        User user = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg","Y");
        PageResponseDto<List<HashtagResponseDto>> hashtagList = hashtagService.getHashtagList(0, 3, user);

        assertThat(hashtagList.getCurrentPage()).isEqualTo(0);
        assertThat(hashtagList.getData().size()).isEqualTo(3);
        assertThat(hashtagList.getTotalCount()).isEqualTo(4);
    }

    @Test
    public void getFixedHashtagList(){
        hashtagRepository.save(new Hashtag("주변맛집"));
        hashtagRepository.save(new Hashtag("등산용품"));
        hashtagRepository.save(new Hashtag("등산데이트"));
        hashtagRepository.save(new Hashtag("등산친구"));
        hashtagRepository.save(new Hashtag("봄산"));
        hashtagRepository.save(new Hashtag("여름산"));
        hashtagRepository.save(new Hashtag("가을산"));
        hashtagRepository.save(new Hashtag("겨울산"));
        hashtagRepository.save(new Hashtag("안전수칙"));
        hashtagRepository.save(new Hashtag("산악회"));
        hashtagRepository.save(new Hashtag("10~20대"));
        hashtagRepository.save(new Hashtag("30~40대"));
        hashtagRepository.save(new Hashtag("50~60대"));
        hashtagRepository.save(new Hashtag("등산꿀팁"));
        hashtagRepository.save(new Hashtag("산추천"));
        hashtagRepository.save(new Hashtag("계곡추천"));
        hashtagRepository.save(new Hashtag("등린이"));
        hashtagRepository.save(new Hashtag("등산입문자"));
        hashtagRepository.save(new Hashtag("등산매니아"));
        hashtagRepository.save(new Hashtag("등산모임"));
        hashtagRepository.save(new Hashtag("1시간걸리는산"));
        hashtagRepository.save(new Hashtag("2시간걸리는산"));
        hashtagRepository.save(new Hashtag("3시간걸리는산"));

        List<HashtagResponseDto> fixedHashtagList = hashtagService.getFixedHashtagList();

        assertThat(fixedHashtagList.size()).isEqualTo(23);
    }
}