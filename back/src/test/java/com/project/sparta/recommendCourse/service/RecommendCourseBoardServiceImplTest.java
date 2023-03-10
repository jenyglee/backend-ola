package com.project.sparta.recommendCourse.service;


import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        User admin1 = createAdmin();
        User user = createUser();

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
        Long boardId = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, user.getId());
        Long BoardId2 = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, admin1.getId());

        //then
        Optional<RecommendCourseBoard> recommendCourseBoard = recommendCourseBoardRepository.findById(boardId);
        Optional<RecommendCourseBoard> recommendCourseBoard2 = recommendCourseBoardRepository.findById(BoardId2);
        assertThat(recommendCourseBoard.get().getId()).isEqualTo(boardId);
        assertThat(recommendCourseBoard.get().getAltitude()).isEqualTo(800);
        assertThat(recommendCourseBoard.get().getUserId()).isEqualTo(user.getId());
        assertThat(recommendCourseBoard2.get().getId()).isEqualTo(BoardId2);
        assertThat(recommendCourseBoard2.get().getAltitude()).isEqualTo(800);
        assertThat(recommendCourseBoard2.get().getUserId()).isEqualTo(admin1.getId());
    }

    @Test
    @DisplayName("추천코스 게시글 수정")
    @Transactional
    void modifyRecommendCourseBoard() {
        //given
        //작성자 생성
        User admin1 = createAdmin();

        //유저 작성자 생성
        User user = createUser();

        //회원 등급변경 Dto생성
        UserGradeDto userGradeDto = new UserGradeDto(2);

        //회원 등급변경
        userService.changeGrade(userGradeDto,user.getId());

        //이미지 리스트 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");

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


        //글작성
        RecommendCourseBoard board1 = createBoard(user);
        RecommendCourseBoard board2 = createBoard(admin1);
        //when

        //글 수정
        recommendCourseBoardService.modifyRecommendCourseBoard(board2.getId(),recommendRequestDto2,admin1.getId());
        recommendCourseBoardService.modifyRecommendCourseBoard(board1.getId(),recommendRequestDto2,user.getId());

        Optional<RecommendCourseBoard> recommendCourseBoard = recommendCourseBoardRepository.findById(board2.getId());
        Optional<RecommendCourseBoard> recommendCourseBoard2 = recommendCourseBoardRepository.findById(board1.getId());


        //then

        assertThat(recommendCourseBoard.get().getId()).isEqualTo(board2.getId());
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
        User admin1 = createAdmin();

        //이미지 리스트 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");
        //글작성
        RecommendCourseBoard board = createBoard(admin1);


        //when
        recommendCourseBoardService.deleteRecommendCourseBoard(board.getId(),admin1.getId());
        //then
        // 삭제된 글을 어떻게 테스트코드로 확인하지...?
        Optional<RecommendCourseBoard> board2 = recommendCourseBoardRepository.findById(board.getId());
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
        //유저 작성자 생성
        User user = createUser();

        //회원 등급변경 Dto생성
        UserGradeDto userGradeDto = new UserGradeDto(2);

        //회원 등급변경
        userService.changeGrade(userGradeDto,user.getId());

        //글작성
        RecommendCourseBoard board1 = createBoard(user);
        RecommendCourseBoard board2 = createBoard(user);
        RecommendCourseBoard board3 = createBoard(user);
        RecommendCourseBoard board4 = createBoard(user);
        RecommendCourseBoard board5 = createBoard(user);

        PageRequest pageRequest = PageRequest.of(0,8);

        //when
        PageResponseDto<List<RecommendResponseDto>> myRecommendCourseBoard =
                recommendCourseBoardService.getMyRecommendCourseBoard(pageRequest.getPageNumber(), pageRequest.getPageSize(), user);

        //then
        assertThat(myRecommendCourseBoard.getTotalCount()).isEqualTo(5);
    }

    @Test
    @DisplayName("어드민 코스 수정")
    @Transactional
    void adminRecommendBoardUpdate() {
        //given
        //작성자 생성
        User admin1 = createAdmin();

        //이미지 리스트 생성
        List imageList = List.of("https://cdn.mhns.co.kr/news/photo/201910/313897_420232_1924.jpg");
        //글작성
        RecommendCourseBoard board = createBoard(admin1);

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
        //글 수정
        recommendCourseBoardService.adminRecommendBoardUpdate(board.getId(),recommendRequestDto2);

        //then
        Optional<RecommendCourseBoard> recommendCourseBoard = recommendCourseBoardRepository.findById(board.getId());

        assertThat(recommendCourseBoard.get().getId()).isEqualTo(board.getId());
        assertThat(recommendCourseBoard.get().getTitle()).isEqualTo("테스트코드 제목2");
        assertThat(recommendCourseBoard.get().getSeason()).isEqualTo("겨울");
    }

    @Test
    @DisplayName("어드민 코스 삭제")
    @Transactional
    void adminRecommendBoardDelete() {
        //given
        //작성자 생성
        User admin1 = createAdmin();

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

        //글작성
        RecommendCourseBoard board = createBoard(admin1);

        //when
        recommendCourseBoardService.adminRecommendBoardDelete(board.getId());

        //then
        Optional<RecommendCourseBoard> board2 = recommendCourseBoardRepository.findById(board.getId());
        assertThat(board2).isEmpty();
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

    //어드민생성 메서드
    public User createAdmin(){
        String randomUser = "admin"+ UUID.randomUUID().toString().substring(0,5);
        User admin1 = User.adminBuilder()
                .email("admin11@naver.com")
                .nickName("테스트어드민1")
                .password("asdf12!@")
                .build();
        em.persist(admin1);
        return userRepository.findById(admin1.getId()).get();
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