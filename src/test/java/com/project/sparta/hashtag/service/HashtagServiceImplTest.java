package com.project.sparta.hashtag.service;

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
        User user = new User("1234", "이재원", 20, "01010101", "wodnj@naver.com", UserRoleEnum.USER);
        Hashtag saved = hashtagService.createHashtag("등린이", user);

        assertThat(saved.getName()).isEqualTo("등린이");
    }

    @Test
    public void deleteHashtag() {
        User user = new User("1234", "이재원", 20, "01010101", "wodnj@naver.com", UserRoleEnum.USER);
        Hashtag saved = hashtagService.createHashtag("등린이", user);
        hashtagService.deleteHashtag(saved.getId(), user);

        List<Hashtag> allHashtag = hashtagRepository.findAll();

        assertThat(allHashtag.size()).isEqualTo(0);


        assertThat(saved.getName()).isEqualTo("등린이");
    }
}