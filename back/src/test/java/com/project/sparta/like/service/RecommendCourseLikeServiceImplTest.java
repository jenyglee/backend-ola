package com.project.sparta.like.service;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.like.entity.BoardLike;
import com.project.sparta.like.entity.CourseLike;
import com.project.sparta.like.repository.LikeRecommendRepository;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RecommendCourseLikeServiceImplTest {
    //레파지토리 의존성 주입
    @Autowired
    RecommendCourseBoardRepository recommendCourseBoardRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RecommendCourseBoardRepository courseRepository;

    @Autowired
    LikeRecommendRepository likeRecommendRepository;

    //서비스 의존성 주입
    @Autowired
    RecommendCourseLikeService recommendCourseLikeService;
    @Autowired
    RecommendCourseBoardService recommendCourseBoardService;
    @Autowired
    UserService userService;


    @Autowired
    EntityManager em;

    @Test
    @DisplayName("코스추천 좋아요 추가")
    @Transactional
    void likeRecommendCourse() {
        User user = createUser();
        User user2 = createUser();
        //코스추천 게시글 생성
        RecommendCourseBoard board = createBoard(user2);

        //when
        Long boardLikeId = recommendCourseLikeService.likeRecommendCourse(board.getId(), user);
        //then
        assertThat(likeRecommendRepository.findById(boardLikeId).get().getUserEmail()).isEqualTo(user.getEmail());

    }

    @Test
    @DisplayName("코스추천 좋아요 삭제")
    @Transactional
    void unLikeRecommendCourse() {
        //유저생성
        User user = createUser();
        User user2 = createUser();
        //게시글 생성
        RecommendCourseBoard board = createBoard(user2);
        //라이크 누르기
        Long boardLikeId = recommendCourseLikeService.likeRecommendCourse(board.getId(), user);

        //when
        recommendCourseLikeService.unLikeRecommendCourse(board.getId(),user);

        //then
        Optional<CourseLike> byId = likeRecommendRepository.findById(boardLikeId);
        assertThat(byId).isEmpty();

    }

    //유저생성 메서드
    public User createUser(){
        String randomUser = "user"+ UUID.randomUUID().toString().substring(0,5);
        User user1 = User
                .userBuilder()
                .email(randomUser +"@olaUser.com")
                .password("1234")
                .nickName("사용자"+randomUser)
                .age(99)
                .phoneNumber("010-1111-2222")
                .userImageUrl("sdf.jpg")
                .build();
        em.persist(user1);
        return userRepository.findById(user1.getId()).get();
    }

    //코스추천게시글 생성 메서드
    public RecommendCourseBoard createBoard(User user){

        //회원 등급변경 Dto생성
        UserGradeDto userGradeDto = new UserGradeDto(2);
        //회원 등급변경
        userService.changeGrade(userGradeDto,user.getId());
        //게시글 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");
        RecommendRequestDto recommendRequestDto = RecommendRequestDto.builder()
                .score(5)
                .title("테스트코드 제목1")
                .season("가을")
                .region("경상북도")
                .altitude(800)
                .contents("테스트코드 컨텐츠입니다.")
                .imgList(imageList)
                .build();

        //글작성
        Long boardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, user.getId());

        return recommendCourseBoardRepository.findById(boardId).orElseThrow();

    }
}