package com.project.sparta.admin.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityBoard.repository.CommunityBoardImgRepository;
import com.project.sparta.communityBoard.repository.CommunityTagRepository;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.like.repository.LikeCommentRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
public class AdminCommunityBoardRepositoryTest {
  // TODO 테스트코드 추가 : 게시글 수정 시 Title, Contents 중 ""인 경우(주성)
  @Autowired
  BoardRepository boardRepository;
  @Autowired
  CommunityBoardService communityBoardService;
  @Autowired
  CommunityTagRepository communityTagRepository;
  @Autowired
  CommunityBoardImgRepository communityBoardImgRepository;
  @Autowired
  HashtagRepository hashtagRepository;
  @Autowired
  CommentRepository commentRepository;
  @Autowired
  LikeCommentRepository likeCommentRepository;
  @Autowired
  LikeBoardRepository likeBoardRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  EntityManager em;

  String randomUser = "user"+ UUID.randomUUID() +"@naver.com";
  List taglist = Arrays.asList(1L, 2L, 3L, 4L);
  List imgList = Arrays.asList("1,2,3,4");
  int page=0;
  int size=10;
  Long globalUserId;

  @BeforeEach
  @Transactional
  public void userCreate(){
    User user = User
        .userBuilder()
        .email(randomUser)
        .password("1234")
        .nickName("99번째 사용자")
        .age(99)
        .phoneNumber("010-1111-2222")
        .userImageUrl("sdf.jpg")
        .build();
    userRepository.save(user);
    globalUserId = user.getId();
  }
  CommunityBoardRequestDto requestDto = CommunityBoardRequestDto
      .builder()
      .title("첫번째 게시글")
      .chatMemCnt(0)
      .contents("첫번쨰 컨텐츠")
      .tagList(taglist)
      .imgList(imgList)
      .chatStatus("Y")
      .build();



  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 수정(notFoundID, notFoundTag)")
  public void updateCommunityBoard(){
    //given
    User user1 = userRepository.findById(globalUserId).orElseThrow();

    List taglistOne = Arrays.asList(199L, 299L, 399L, 499L);
    List imgListOne = Arrays.asList("1,2,3,4");

    communityBoardService.createCommunityBoard(requestDto, user1);

    CommunityBoardRequestDto afterRequestDto = CommunityBoardRequestDto
        .builder()
        .title("에프터 첫번쨰 게시글")
        .chatMemCnt(0)
        .contents("에프터 첫번쨰 컨텐츠")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();

    CommunityBoardRequestDto afterRequestDto2 = CommunityBoardRequestDto
        .builder()
        .title("에프터 첫번쨰 게시글")
        .chatMemCnt(0)
        .contents("에프터 첫번쨰 컨텐츠")
        .tagList(taglistOne)
        .imgList(imgListOne)
        .chatStatus("Y")
        .build();

    Long boardIda = 19999L;
    Long boardId =1L;

    //when, then
    assertThrows(CustomException.class, ()-> communityBoardService.adminUpdateCommunityBoard(boardIda, afterRequestDto));
    assertThrows(CustomException.class, ()-> communityBoardService.adminUpdateCommunityBoard(boardId, afterRequestDto2));
  }

  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 삭제(notFoundId)")
  public void deleteCommunityBoard() {
    //given
    Long boardIda = 19999L;

    //when, then
    assertThrows(CustomException.class, ()-> communityBoardService.adminDeleteCommunityBoard(boardIda));
  }
  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 단건조회(NullPointer)")
  public void getCommunityBoard() {
    //given
    Long boardIda = 19999L;
    User user1 = userRepository.findById(globalUserId).orElseThrow();

    //when, then
    assertThrows(NullPointerException.class, ()-> communityBoardService.getCommunityBoard(boardIda, page, size, user1.getNickName()));
  }

  @Test
  @Transactional
  @DisplayName("나의 커뮤니티 보드 조회(NullPointer)")
  public void getMyCommunityBoard() {
    //given
    User nullUser = null;

    //when, then
    assertThrows(NullPointerException.class, ()->communityBoardService.getMyCommunityBoard(page, size, nullUser));
  }

}
