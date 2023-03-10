package com.project.sparta.like.repository;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.exception.CustomException;
import com.project.sparta.like.service.RecommendCourseLikeService;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeRecommendRepositoryTest {
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
    @DisplayName("게시글 존재 확인 에러")
    @Transactional
    void existenceCheckError() {
        //given
        //유저생성
        User user = createUser();
        Long boardId = 1234L;
        //then
        assertThrows(CustomException.class, ()-> recommendCourseLikeService.likeRecommendCourse(boardId, user));
    }

    @Test
    @DisplayName("좋아요 존재 확인 에러")
    @Transactional
    void likeExistenceCheckError() {
        //given
        //유저생성
        User user = createUser();
        User user2 = createUser();
        //게시글 생성
        RecommendCourseBoard board = createBoard(user2);
        Long boardLikeId1 = recommendCourseLikeService.likeRecommendCourse(board.getId(), user);

        assertThrows(CustomException.class, ()-> recommendCourseLikeService.likeRecommendCourse(board.getId(), user));
    }

    @Test
    @DisplayName("내가 누른 좋아요만 삭제")
    @Transactional
    void likeClickUserCheckError() {
        //내가 누른 좋아요가 아닌데도 삭제가 되나..?
        //given
        //유저생성
        User user = createUser();
        User user2 = createUser();
        //게시글 생성
        RecommendCourseBoard board = createBoard(user2);
        Long boardLikeId1 = recommendCourseLikeService.likeRecommendCourse(board.getId(), user);

        //then
        assertThrows(CustomException.class, ()->  recommendCourseLikeService.likeRecommendCourse(boardLikeId1,user2));
    }

    @Test
    @DisplayName("다른 좋아요 클릭했을때 에러")
    @Transactional
    void otherLikeCheckError() {
        //given
        //유저생성
        User user = createUser();
        //게시글 생성
        RecommendCourseBoard board = createBoard(user);
//        Long boardLikeId1 = boardLikeService.likeBoard(board.getId(), user);
        Long boardLikeId1 = 2836L;

        //then
        assertThrows(CustomException.class, ()-> recommendCourseLikeService.likeRecommendCourse(boardLikeId1,user));
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