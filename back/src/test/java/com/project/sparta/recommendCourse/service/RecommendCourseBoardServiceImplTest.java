package com.project.sparta.recommendCourse.service;


import com.project.sparta.like.repository.LikeRecommendRepository;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class RecommendCourseBoardServiceImplTest {

    //의존성 주입
    @Autowired
    private RecommendCourseBoardRepository recommendCourseBoardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikeRecommendRepository likeRecommendRepository;

    @Autowired
    private RecommendCourseBoardServiceImpl recommendCourseBoardService;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("추천코스 게시글 작성")
    void creatRecommendCourseBoard() {
        //given
        //작성자 생성
        User admin1 = User.adminBuilder()
                .email("admin11@naver.com")
                .nickName("테스트어드민")
                .password("asdf12!@")
                .build();
        //이미지 리스트 생성
        List imageList = Arrays.asList("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");
        //게시글 Dto 생성
        RecommendRequestDto recommendRequestDto = RecommendRequestDto.builder()
                .score(5)
                .title("테스트코드 제목1")
                .season("가을")
                .region("경상북도")
                .altitude(800)
                .contents("테스트코드 컨텐츠입니다.")
                .imgList(imageList)
                .build();


        //when
        //서비스코드 실행
        Long boardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());
        assertThat("")

        //then
        Optional<RecommendCourseBoard> recommendCourseBoard = recommendCourseBoardRepository.findById(boardId);







    }



    @Test
    @DisplayName("추천코스 게시글 수정")
    void modifyRecommendCourseBoard() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("추천코스 게시글 삭제")
    void deleteRecommendCourseBoard() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("추천코스 게시글 단건조회")
    void oneSelectRecommendCourseBoard() {
        //given
        //when
        //then
    }

    @Test
    @DisplayName("추천코스 게시글 전체조회")
    void allRecommendCourseBoard() {
        //given
        //when
        //then
    }

    @Test
    void getMyRecommendCourseBoard() {
        //given
        //when
        //then
    }

    @Test
    void adminRecommendBoardUpdate() {
        //given
        //when
        //then
    }

    @Test
    void adminRecommendBoardDelete() {
        //given
        //when
        //then
    }
}