package com.project.sparta.hashtag.service;
import com.project.sparta.exception.CustomException;
import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.sparta.exception.api.Status.INVALID_HASHTAG_NAME;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest

class HashtagServiceImplTest {

     @Autowired
     HashtagService hashtagService;
     @Autowired
     HashtagRepository hashtagRepository;
     @Test
     @Transactional
     public void createHashtag() {
         //given

         String randomUser = "user"+ UUID.randomUUID();
         User user1 = User
                 .userBuilder()
                 .email(randomUser+"@naver.com")
                 .password("1234")
                 .nickName("99번째 사용자")
                 .age(99)
                 .phoneNumber("010-1111-2222")
                 .userImageUrl("sdf.jpg")
                 .build();
         //when
         Hashtag hashtag = hashtagService.createHashtag("등린이44",user1);

         //then

         assertThat(hashtag.getName()).isEqualTo("등린이44");
     }

     @Test
     public void deleteHashtag() {
         String randomUser = "user"+ UUID.randomUUID();
         User user1 = User
                 .userBuilder()
                 .email(randomUser+"@naver.com")
                 .password("1234")
                 .nickName("99번째 사용자")
                 .age(99)
                 .phoneNumber("010-1111-2222")
                 .userImageUrl("sdf.jpg")
                 .build();

         Hashtag hashtag = hashtagService.createHashtag("등린이44",user1);

         //boardDelete 하기 전 boardSize
         Long beforeBoardSize= hashtagRepository.count();

         hashtagService.deleteHashtag(hashtag.getId(),user1);

         //boardDelete 하고 나서 boardSize
         Long afterBoardSize= hashtagRepository.count();

         //then
         assertThat(afterBoardSize).isLessThan(beforeBoardSize);
     }


//     @Test
//     public void getHashtagList(){
//         String randomUser = "user"+ UUID.randomUUID();
//         User user1 = User
//                 .userBuilder()
//                 .email(randomUser+"@naver.com")
//                 .password("1234")
//                 .nickName("99번째 사용자")
//                 .age(99)
//                 .phoneNumber("010-1111-2222")
//                 .userImageUrl("sdf.jpg")
//                 .build();
//
//         Hashtag hashtag = hashtagService.createHashtag("등린이44",user1);
//         List<Hashtag> allHashtag = hashtagRepository.findAll();
//         assertThat(allHashtag.size()).isEqualTo(1);
//
//
//     }

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