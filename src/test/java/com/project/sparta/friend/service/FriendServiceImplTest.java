package com.project.sparta.friend.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.user.entity.QUser;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.List;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FriendServiceImplTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Autowired
    FriendService friendService;

    @Autowired
    FriendRepository friendRepository;


    @BeforeEach
    public void Befor() {
        Hashtag tag1 = new Hashtag("등린이");
        Hashtag tag2 = new Hashtag("절경");
        Hashtag tag3 = new Hashtag("등산용품");
        Hashtag tag4 = new Hashtag("산악회");
        Hashtag tag5 = new Hashtag("정복");
        Hashtag tag6 = new Hashtag("고급코스");
        Hashtag tag7 = new Hashtag("짧은코스");
        Hashtag tag8 = new Hashtag("휴양등산");
        Hashtag tag9 = new Hashtag("예술풍경");
        Hashtag tag10 = new Hashtag("20대인기");

        em.persist(tag1);
        em.persist(tag2);
        em.persist(tag3);
        em.persist(tag4);
        em.persist(tag5);
        em.persist(tag6);
        em.persist(tag7);
        em.persist(tag8);
        em.persist(tag9);
        em.persist(tag10);


        User user1 = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg",USER_REGISTERED);
        User user2 = new User("1234", "한세인", 20, "010-1234-1235", "user2@naver.com", UserRoleEnum.USER, "user2.jpg",USER_REGISTERED);
        User user3 = new User("1234", "김민선", 30, "010-1234-1236", "user3@naver.com", UserRoleEnum.USER, "user3.jpg",USER_REGISTERED);
        User user4 = new User("1234", "김주성", 20, "010-1234-1237", "user4@naver.com", UserRoleEnum.USER, "user4.jpg",USER_REGISTERED);
        User user5 = new User("1234", "김두영", 30, "010-1234-1238", "user5@naver.com", UserRoleEnum.USER, "user5.jpg",USER_REGISTERED);
        User user6 = new User("1234", "한병두", 20, "010-1234-1239", "user6@naver.com", UserRoleEnum.USER, "user6.jpg",USER_REGISTERED);
        User user7 = new User("1234", "한지민", 10, "010-1234-1222", "user7@naver.com", UserRoleEnum.USER, "user7.jpg",USER_REGISTERED);
        User user8 = new User("1234", "한예슬", 20, "010-1234-1213", "user8@naver.com", UserRoleEnum.USER, "user8.jpg",USER_REGISTERED);
        User user9 = new User("1234", "한예리", 30, "010-1234-1255", "user9@naver.com", UserRoleEnum.USER, "user9.jpg",USER_REGISTERED);
        User user10 = new User("1234", "한호영", 20, "010-1234-1227", "user10@naver.com", UserRoleEnum.USER, "user10.jpg",USER_REGISTERED);
        User user11 = new User("1234", "한민선", 30, "010-1234-1211", "user11@naver.com", UserRoleEnum.USER, "user11.jpg",USER_REGISTERED);

        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        em.persist(user5);
        em.persist(user6);
        em.persist(user7);
        em.persist(user8);
        em.persist(user9);
        em.persist(user10);
        em.persist(user11);

        UserTag userTag1 = new UserTag(user1, tag1);
        UserTag userTag2 = new UserTag(user1, tag2);
        UserTag userTag3 = new UserTag(user1, tag3);
        UserTag userTag4 = new UserTag(user1, tag4);
        UserTag userTag5 = new UserTag(user1, tag5);
        UserTag userTag6 = new UserTag(user2, tag6);
        UserTag userTag7 = new UserTag(user2, tag7);
        UserTag userTag8 = new UserTag(user2, tag3);
        UserTag userTag9 = new UserTag(user2, tag4);
        UserTag userTag10 = new UserTag(user2, tag5);

        em.persist(userTag1);
        em.persist(userTag2);
        em.persist(userTag3);
        em.persist(userTag4);
        em.persist(userTag5);
        em.persist(userTag6);
        em.persist(userTag7);
        em.persist(userTag8);
        em.persist(userTag9);
        em.persist(userTag10);


        //초기화
        em.flush();
        em.clear();
    }

    @Test
    public void addFriend(){
        friendService.addFriend(1L, "한세인");
        friendService.addFriend(1L, "김민선");
        List<Friend> friend= friendRepository.findByUserId(1L);

        Assertions.assertThat(friend.get(0).getTargetId()).isEqualTo(2L);
        Assertions.assertThat(friend.get(1).getTargetId()).isEqualTo(3L);
    }


    @Test
    public void deleteFriend(){
        friendService.addFriend(1L, "한세인");
        friendService.addFriend(1L, "김민선");

        friendService.deleteFriend(2L);
        List<Friend> friend = friendRepository.findByUserId(1L);

        Assertions.assertThat(friend.size()).isEqualTo(1);
    }

    @Test
    public void searchFriend(){
        friendService.addFriend(1L, "한세인");
        friendService.addFriend(1L, "김민선");

        PageResponseDto result = friendService.searchFriend(0, 5, "한");
        Assertions.assertThat(result.getCurrentPage()).isEqualTo(0);
        Assertions.assertThat(result.getTotalCount()).isEqualTo(5);
    }

    @Test
    void allMyFriendList() {
        //해당 유저의 회원가입 태그 정보 긁어오기
        User userInfo = userRepository.findById(1L).orElseThrow(()-> new CustomException(INVALID_USER));

        //비교할 회원 랜덤으로 뽑아오기
        List<User> randomList = friendRepository.randomUser(USER_REGISTERED);

        System.out.println(randomList.size());
        System.out.println(randomList.get(0).getTags());
        System.out.println(userInfo.getTags().get(0).getTag().getId());
        System.out.println(userInfo.getTags().get(0).getTag().getName());

        //Assertions.assertThat(randomList.size()).isEqualTo(5);
    }

    @Test
    void allRecomentFriendList() {
        List<RecommentFriendResponseDto> result = friendService.AllRecomentFriendList(1L);
        Assertions.assertThat(result.get(0).getMatchingSize()).isEqualTo(3);
    }
}