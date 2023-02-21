package com.project.sparta.hashtag.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class HashtagServiceImplTest {

    // @Autowired
    // HashtagService hashtagService;
    // @Autowired
    // HashtagRepository hashtagRepository;
    // @Test
    // public void createHashtag() {
    //     User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
    //
    //     Hashtag saved = hashtagService.createHashtag("등린이", user1);
    //
    //     assertThat(saved.getName()).isEqualTo("등린이");
    // }
    //
    // @Test
    // public void deleteHashtag() {
    //     User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
    //     Hashtag saved = hashtagService.createHashtag("등린이", user1);
    //     hashtagService.deleteHashtag(saved.getId(), user1);
    //
    //     List<Hashtag> allHashtag = hashtagRepository.findAll();
    //
    //     assertThat(allHashtag.size()).isEqualTo(0);
    //
    //
    //     assertThat(saved.getName()).isEqualTo("등린이");
    // }
    //
    // @Test
    // public void getHashtagList(){
    //     User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
    //     hashtagService.createHashtag("등린이", user1);
    //
    //     List<Hashtag> allHashtag = hashtagRepository.findAll();
    //     assertThat(allHashtag.size()).isEqualTo(1);
    //
    //     // 해시태그에 페이징 필요
    // }
    //
    // @Test
    // public void getFixedHashtagList(){
    //     hashtagRepository.save(new Hashtag("주변맛집"));
    //     hashtagRepository.save(new Hashtag("등산용품"));
    //     hashtagRepository.save(new Hashtag("등산데이트"));
    //     hashtagRepository.save(new Hashtag("등산친구"));
    //     hashtagRepository.save(new Hashtag("봄산"));
    //     hashtagRepository.save(new Hashtag("여름산"));
    //     hashtagRepository.save(new Hashtag("가을산"));
    //     hashtagRepository.save(new Hashtag("겨울산"));
    //     hashtagRepository.save(new Hashtag("안전수칙"));
    //     hashtagRepository.save(new Hashtag("산악회"));
    //     hashtagRepository.save(new Hashtag("10~20대"));
    //     hashtagRepository.save(new Hashtag("30~40대"));
    //     hashtagRepository.save(new Hashtag("50~60대"));
    //     hashtagRepository.save(new Hashtag("등산꿀팁"));
    //     hashtagRepository.save(new Hashtag("산추천"));
    //     hashtagRepository.save(new Hashtag("계곡추천"));
    //     hashtagRepository.save(new Hashtag("등린이"));
    //     hashtagRepository.save(new Hashtag("등산입문자"));
    //     hashtagRepository.save(new Hashtag("등산매니아"));
    //     hashtagRepository.save(new Hashtag("등산모임"));
    //     hashtagRepository.save(new Hashtag("1시간걸리는산"));
    //     hashtagRepository.save(new Hashtag("2시간걸리는산"));
    //     hashtagRepository.save(new Hashtag("3시간걸리는산"));
    //
    //     List<HashtagResponseDto> fixedHashtagList = hashtagService.getFixedHashtagList();
    //
    //     assertThat(fixedHashtagList.size()).isEqualTo(23);
    // }
}