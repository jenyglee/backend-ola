package com.project.sparta.admin.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardServiceImpl;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.service.UserService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AdminRecommandBoardRepositoryTest {
  //이셉션 에러를 확인하기위한 테스트는 레파지토리 테스트가 아니라 서비스테스트 아닌가..? 왜 레파지토리 테스트로 짜야하는지 다시 물어봐야지
  @Autowired
  private RecommendCourseBoardServiceImpl recommendCourseBoardService;

  @Autowired
  private UserService userService;

  @PersistenceContext
  private EntityManager em;


  @Test
  @DisplayName("어드민 코스추천 게시글수정 작성자확인 에러")
  @Transactional
  void modifyRecommendCourseBoardError1() {

    //유저 작성자 생성
    User user = User.userBuilder()
        .email("qweqsfsd1235@user.com")
        .nickName("유저닉네임")
        .password("dhffk12!@")
        .age(29)
        .phoneNumber("01064538453")
        .userImageUrl("https://mblogthumb-phinf.pstatic.net/20160625_240/bjy0524_146683775176259uj4_JPEG/attachImage_312025754.jpeg?type=w800")
        .build();
    em.persist(user);

    User user2 = User.userBuilder()
        .email("sadfasdf235@user.com")
        .nickName("유저닉네임")
        .password("dhffk12!@")
        .age(29)
        .phoneNumber("01064538453")
        .userImageUrl("https://mblogthumb-phinf.pstatic.net/20160625_240/bjy0524_146683775176259uj4_JPEG/attachImage_312025754.jpeg?type=w800")
        .build();
    em.persist(user2);

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

    //회원 등급변경
    userService.changeGrade(userGradeDto,user.getId());
    userService.changeGrade(userGradeDto,user2.getId());
    //글작성
    Long boardId2 = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, user.getId());
    //when

    //then

    //본인이 쓴 글 아니면 수정되면 안됨.
    Assertions.assertThrows(CustomException.class,()->
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId2,recommendRequestDto,user2.getId()));
  }

  @Test
  @DisplayName("어드민 코스추천 게시글수정 빈값 에러")
  @Transactional
  void modifyRecommendCourseBoardError2() {

    //유저 작성자 생성
    User user = User.userBuilder()
        .email("qweqsfsd1235@user.com")
        .nickName("유저닉네임")
        .password("dhffk12!@")
        .age(29)
        .phoneNumber("01064538453")
        .userImageUrl("https://mblogthumb-phinf.pstatic.net/20160625_240/bjy0524_146683775176259uj4_JPEG/attachImage_312025754.jpeg?type=w800")
        .build();
    em.persist(user);

    User user2 = User.userBuilder()
        .email("sadfasdf235@user.com")
        .nickName("유저닉네임")
        .password("dhffk12!@")
        .age(29)
        .phoneNumber("01064538453")
        .userImageUrl("https://mblogthumb-phinf.pstatic.net/20160625_240/bjy0524_146683775176259uj4_JPEG/attachImage_312025754.jpeg?type=w800")
        .build();
    em.persist(user2);

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

    //score 값 안들어갔을때,
    RecommendRequestDto recommendRequestDto3 = RecommendRequestDto.builder()
        .title("테스트코드 제목2")
        .season("겨울")
        .region("경상북도")
        .altitude(800)
        .contents("테스트코드 컨텐츠입니다.")
        .imgList(imageList)
        .build();

    //title 값 안들어갔을때
    RecommendRequestDto recommendRequestDto4 = RecommendRequestDto.builder()
        .score(5)
        .season("겨울")
        .region("경상북도")
        .altitude(800)
        .contents("테스트코드 컨텐츠입니다.")
        .imgList(imageList)
        .build();

    //season 값 안들어갔을때
    RecommendRequestDto recommendRequestDto5 = RecommendRequestDto.builder()
        .score(5)
        .title("테스트코드 제목2")
        .region("경상북도")
        .altitude(800)
        .contents("테스트코드 컨텐츠입니다.")
        .imgList(imageList)
        .build();

    //region 값 안들어갔을때
    RecommendRequestDto recommendRequestDto6 = RecommendRequestDto.builder()
        .score(5)
        .title("테스트코드 제목2")
        .season("겨울")
        .altitude(800)
        .contents("테스트코드 컨텐츠입니다.")
        .imgList(imageList)
        .build();

    //altitude 값 안들어갔을때
    RecommendRequestDto recommendRequestDto7 = RecommendRequestDto.builder()
        .score(5)
        .title("테스트코드 제목2")
        .season("겨울")
        .region("경상북도")
        .contents("테스트코드 컨텐츠입니다.")
        .imgList(imageList)
        .build();

    //contents 값 안들어갔을때
    RecommendRequestDto recommendRequestDto8 = RecommendRequestDto.builder()
        .score(5)
        .title("테스트코드 제목2")
        .season("겨울")
        .region("경상북도")
        .altitude(800)
        .imgList(imageList)
        .build();


    //회원 등급변경
    userService.changeGrade(userGradeDto,user.getId());
    userService.changeGrade(userGradeDto,user2.getId());
    //글작성
    Long boardId2 = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, user.getId());

    //when

    //then

    //빈값 들어갔을때 커스텀이셉션 잘 뜨나..?
    Assertions.assertThrows(CustomException.class,()->
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId2,recommendRequestDto3,user2.getId()));
    Assertions.assertThrows(CustomException.class,()->
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId2,recommendRequestDto4,user2.getId()));
    Assertions.assertThrows(CustomException.class,()->
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId2,recommendRequestDto5,user2.getId()));
    Assertions.assertThrows(CustomException.class,()->
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId2,recommendRequestDto6,user2.getId()));
    Assertions.assertThrows(CustomException.class,()->
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId2,recommendRequestDto7,user2.getId()));
    Assertions.assertThrows(CustomException.class,()->
        recommendCourseBoardService.modifyRecommendCourseBoard(boardId2,recommendRequestDto8,user2.getId()));

  }

  @Test
  @DisplayName("어드민 코스추천 게시글삭제 작성자 확인 에러")
  @Transactional
  void deleteRecommendCourseBoardError() {
    //given
    //유저 작성자 생성
    User user = User.userBuilder()
        .email("qweqsfsd1235@user.com")
        .nickName("유저닉네임")
        .password("dhffk12!@")
        .age(29)
        .phoneNumber("01064538453")
        .userImageUrl("https://mblogthumb-phinf.pstatic.net/20160625_240/bjy0524_146683775176259uj4_JPEG/attachImage_312025754.jpeg?type=w800")
        .build();
    em.persist(user);
    User user2 = User.userBuilder()
        .email("wadxfvawr223@user.com")
        .nickName("유저닉네임")
        .password("dhffk12!@")
        .age(29)
        .phoneNumber("01064538453")
        .userImageUrl("https://mblogthumb-phinf.pstatic.net/20160625_240/bjy0524_146683775176259uj4_JPEG/attachImage_312025754.jpeg?type=w800")
        .build();
    em.persist(user2);

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

    //회원 등급변경
    userService.changeGrade(userGradeDto,user.getId());
    //글작성
    Long boardId2 = recommendCourseBoardService.creatRecommendCourseBoard(recommendRequestDto, user.getId());
    //when

    //then
    //본인이 쓴 글 아니면 삭제되면 안됨.
    Assertions.assertThrows(CustomException.class,()->
        recommendCourseBoardService.deleteRecommendCourseBoard(boardId2,user2.getId()));
  }

}
