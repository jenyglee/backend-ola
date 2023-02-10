package com.project.sparta.friend.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.friend.dto.RecommentFriendResponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.user.entity.*;
import com.project.sparta.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import java.util.List;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;
import static com.project.sparta.exception.api.Status.INVALID_USER;

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
    @Autowired
    private UserRepository userRepository;


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


        User user1 = new User("user1@naver.com","1234", "이재원", UserRoleEnum.USER, USER_REGISTERED, 10,"010-1234-1234","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user2 = new User("user2@naver.com","1234", "한세인", UserRoleEnum.USER, USER_REGISTERED, 20,"010-1234-1235","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user3 = new User("user3@naver.com","1234", "김민선", UserRoleEnum.USER, USER_REGISTERED, 30,"010-1234-1236","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user4 = new User("user4@naver.com","1234", "김주성", UserRoleEnum.USER, USER_REGISTERED, 10,"010-1234-1237","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user5 = new User("user5@naver.com","1234", "김두영", UserRoleEnum.USER, USER_REGISTERED, 20,"010-1234-1238","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user6 = new User("user6@naver.com","1234", "한병두", UserRoleEnum.USER, USER_REGISTERED, 40,"010-1234-1239","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user7 = new User("user7@naver.com","1234", "한효주", UserRoleEnum.USER, USER_REGISTERED, 30,"010-1234-1240","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user8 = new User("user8@naver.com","1234", "한지민", UserRoleEnum.USER, USER_REGISTERED, 20,"010-1234-1241","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user9 = new User("user9@naver.com","1234", "한예슬", UserRoleEnum.USER, USER_REGISTERED, 10,"010-1234-1242","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user10 = new User("user10@naver.com","1234", "한미란", UserRoleEnum.USER, USER_REGISTERED, 20,"010-1234-1243","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);
        User user11 = new User("user11@naver.com","1234", "한영두", UserRoleEnum.USER, USER_REGISTERED, 10,"010-1234-1244","sdf.jpg", UserGradeEnum.MOUNTAIN_GOD);


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

        PageRequest pageRequest = PageRequest.of(0, 100, Sort.by(Sort.Direction.ASC, "id"));
        //해당 유저의 회원가입 태그 정보 긁어오기
        //비교할 회원 랜덤으로 뽑아오기
        Page<User> randomList = friendRepository.randomUser(userInfo, USER_REGISTERED, pageRequest);

//        System.out.println(randomList.size());
//        System.out.println(randomList.get(0).getTags().get(0).getTag().getName());
//        System.out.println(userInfo.getTags().get(0).getTag().getId());
//        System.out.println(userInfo.getTags().get(0).getTag().getName());

        //Assertions.assertThat(randomList.size()).isEqualTo(5);
    }

    @Test
    void allRecomentFriendList() {
//        List<RecommentFriendResponseDto> result = friendService.AllRecomentFriendList(1L);
//        Assertions.assertThat(result.get(0).getMatchingSize()).isEqualTo(3);
    }
}