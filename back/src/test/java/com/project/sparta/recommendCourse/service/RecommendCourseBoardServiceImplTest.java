package com.project.sparta.recommendCourse.service;


import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest

class RecommendCourseBoardServiceImplTest {

    //의존성 주입
    @Autowired
    private RecommendCourseBoardRepository recommendCourseBoardRepository;
    @Autowired
    private RecommendCourseBoardServiceImpl recommendCourseBoardService;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("추천코스 게시글 작성")
    @Transactional
    void creatRecommendCourseBoard() {
        //given
        //작성자 생성
        User admin1 = User.adminBuilder()
                .email("admin11@naver.com")
                .nickName("테스트어드민")
                .password("asdf12!@")
                .build();
        em.persist(admin1);

        User user = User.userBuilder()
                .email("user1@user.com")
                .nickName("유저닉네임")
                .password("dhffk12!@")
                .age(29)
                .phoneNumber("01064538453")
                .userImageUrl("https://mblogthumb-phinf.pstatic.net/20160625_240/bjy0524_146683775176259uj4_JPEG/attachImage_312025754.jpeg?type=w800")
                .build();
        em.persist(user);

        //이미지 리스트 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");

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

        //회원 등급변경 Dto생성
        UserGradeDto userGradeDto = new UserGradeDto(2);

        //when
        //회원 등급변경
        userService.changeGrade(userGradeDto,user.getId());

        //서비스코드 실행
        Long userBoardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, user.getId());
        Long boardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, user.getId());

        //then
        Optional<RecommendCourseBoard> recommendCourseBoard = recommendCourseBoardRepository.findById(boardId);
        assertThat(recommendCourseBoard.get().getId()).isEqualTo(boardId);
        assertThat(recommendCourseBoard.get().getAltitude()).isEqualTo(800);
        assertThat(recommendCourseBoard.get().getUserId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("추천코스 게시글 수정")
    @Transactional
    void modifyRecommendCourseBoard() {
        //given
        //작성자 생성
        User admin1 = User.adminBuilder()
                .email("admin11@naver.com")
                .nickName("테스트어드민1")
                .password("asdf12!@")
                .build();
        User admin2 = User.adminBuilder()
                .email("admin12@naver.com")
                .nickName("테스트어드민2")
                .password("asdf12!@")
                .build();
        em.persist(admin1);
        em.persist(admin2);

        //유저 작성자 생성
        User user = User.userBuilder()
                .email("user1@user.com")
                .nickName("유저닉네임")
                .password("dhffk12!@")
                .age(29)
                .phoneNumber("01064538453")
                .userImageUrl("https://mblogthumb-phinf.pstatic.net/20160625_240/bjy0524_146683775176259uj4_JPEG/attachImage_312025754.jpeg?type=w800")
                .build();
        em.persist(user);

        //회원 등급변경 Dto생성
        UserGradeDto userGradeDto = new UserGradeDto(2);

        //이미지 리스트 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");
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
        //수정된 게시글 Dto 생성
        RecommendRequestDto recommendRequestDto2 = RecommendRequestDto.builder()
                .score(5)
                .title("테스트코드 제목2")
                .season("겨울")
                .region("경상북도")
                .altitude(800)
                .contents("테스트코드 컨텐츠입니다.")
                .imgList(imageList)
                .build();
        //when
        //회원 등급변경
        userService.changeGrade(userGradeDto,user.getId());

        //글작성
        Long boardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());
        Long boardId2 = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, user.getId());
        //글 수정
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId,recommendRequestDto2,admin1.getId());
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId2,recommendRequestDto2,user.getId());

        Optional<RecommendCourseBoard> recommendCourseBoard = recommendCourseBoardRepository.findById(boardId);
        Optional<RecommendCourseBoard> recommendCourseBoard2 = recommendCourseBoardRepository.findById(boardId2);


        //then

//        //본인이 쓴 글 아니면 수정되면 안됨.
//        Assertions.assertThrows(CustomException.class,()->
//                recommendCourseBoardService.modifyRecommendCourseBoard(boardId,recommendRequestDto,admin2.getId()));

        assertThat(recommendCourseBoard.get().getId()).isEqualTo(boardId);
        assertThat(recommendCourseBoard.get().getTitle()).isEqualTo("테스트코드 제목2");
        assertThat(recommendCourseBoard.get().getSeason()).isEqualTo("겨울");
        assertThat(recommendCourseBoard2.get().getTitle()).isEqualTo("테스트코드 제목2");
        assertThat(recommendCourseBoard2.get().getSeason()).isEqualTo("겨울");
        assertThat(recommendCourseBoard2.get().getUserId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("추천코스 게시글 삭제")
    @Transactional
    void deleteRecommendCourseBoard() {
        //given
        //작성자 생성
        User admin1 = User.adminBuilder()
                .email("admin11@naver.com")
                .nickName("테스트어드민1")
                .password("asdf12!@")
                .build();
        em.persist(admin1);

        //이미지 리스트 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");
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
        //글작성
        Long boardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());
        recommendCourseBoardService.deleteRecommendCourseBoard(boardId,admin1.getId());
        //then
        // 삭제된 글을 어떻게 테스트코드로 확인하지...?
        Optional<RecommendCourseBoard> board2 = recommendCourseBoardRepository.findById(boardId);
        assertThat(board2).isEmpty();
    }

    @Test
    @DisplayName("추천코스 게시글 단건조회")
    void oneSelectRecommendCourseBoard() {
        //given
        Long boardId = 30L;
        User user1 = userRepository.findById(4L).orElseThrow();
        //when
        RecommendDetailResponseDto recommendDetailResponseDto = recommendCourseBoardService.oneSelectRecommendCourseBoard(boardId, user1.getNickName());
        //then
        assertThat(recommendDetailResponseDto.getRegion()).isEqualTo("제주도 서귀포시 토평동 산15-1");
        assertThat(recommendDetailResponseDto.getScore()).isEqualTo(3);
        assertThat(recommendDetailResponseDto.getSeason()).isEqualTo("겨울");

    }


    @Test
    @DisplayName("추천코스 게시글 전체조회")
    void allRecommendCourseBoard() {
        //given
        int page = 0;
        int size = 10;
        int score = 5;
        String season = null;
        int altitude = 0;
        String region = null;
        String sort = null;

        //when
        PageResponseDto<List<RecommendResponseDto>> listPageResponseDto = recommendCourseBoardService.allRecommendCourseBoard(page, size, score, season, altitude, region, sort);

        //then
        assertThat(listPageResponseDto.getTotalCount()).isEqualTo(5);
    }

    @Test
    @DisplayName("내가 쓴 코스 추천 조회")
    @Transactional
    void getMyRecommendCourseBoard() {
        //given
        User admin1 = User.adminBuilder()
                .email("admin11@naver.com")
                .nickName("테스트어드민1")
                .password("asdf12!@")
                .build();
        em.persist(admin1);

        //이미지 리스트 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");
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

        Long boardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());
        Long boardId2 = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());
        Long boardId3 = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());
        Long boardId4 = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());
        PageRequest pageRequest = PageRequest.of(0,8);

        //when
        PageResponseDto<List<RecommendResponseDto>> myRecommendCourseBoard = recommendCourseBoardService.getMyRecommendCourseBoard(pageRequest.getPageNumber(), pageRequest.getPageSize(), admin1);

        //then
        assertThat(myRecommendCourseBoard.getTotalCount()).isEqualTo(4);
    }

    @Test
    @DisplayName("어드민 코스 수정")
    @Transactional
    void adminRecommendBoardUpdate() {
        //given
        //작성자 생성
        User admin1 = User.adminBuilder()
                .email("admin11@naver.com")
                .nickName("테스트어드민1")
                .password("asdf12!@")
                .build();
        User admin2 = User.adminBuilder()
                .email("admin12@naver.com")
                .nickName("테스트어드민2")
                .password("asdf12!@")
                .build();
        em.persist(admin1);
        em.persist(admin2);

        //이미지 리스트 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");
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
        //수정된 게시글 Dto 생성
        RecommendRequestDto recommendRequestDto2 = RecommendRequestDto.builder()
                .score(5)
                .title("테스트코드 제목2")
                .season("겨울")
                .region("경상북도")
                .altitude(800)
                .contents("테스트코드 컨텐츠입니다.")
                .imgList(imageList)
                .build();
        //when
        //글작성
        Long boardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());

        //글 수정
        recommendCourseBoardService.adminRecommendBoardUpdate(boardId,recommendRequestDto2);

        //then
        Optional<RecommendCourseBoard> recommendCourseBoard = recommendCourseBoardRepository.findById(boardId);

        assertThat(recommendCourseBoard.get().getId()).isEqualTo(boardId);
        assertThat(recommendCourseBoard.get().getTitle()).isEqualTo("테스트코드 제목2");
        assertThat(recommendCourseBoard.get().getSeason()).isEqualTo("겨울");
    }

    @Test
    @DisplayName("어드민 코스 삭제")
    @Transactional
    void adminRecommendBoardDelete() {
        //given
        //작성자 생성
        User admin1 = User.adminBuilder()
                .email("admin11@naver.com")
                .nickName("테스트어드민1")
                .password("asdf12!@")
                .build();
        em.persist(admin1);

        //이미지 리스트 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");
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
        //글작성
        Long boardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());
        Optional<RecommendCourseBoard> board = recommendCourseBoardRepository.findById(boardId);
        recommendCourseBoardService.adminRecommendBoardDelete(boardId);
        //then
        // 삭제된 글을 어떻게 테스트코드로 확인하지...?
//        assertThat(board).isEmpty();
//        assertThat(recommendCourseBoardRepository.findById(boardId).equals(null)).isTrue();
        Optional<RecommendCourseBoard> board2 = recommendCourseBoardRepository.findById(boardId);
        assertThat(board2).isEmpty();
    }
}