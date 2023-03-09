package com.project.sparta.communityBoard.repository;

import static com.project.sparta.exception.api.Status.NOT_FOUND_COMMUNITY_BOARD;
import static com.project.sparta.exception.api.Status.NOT_FOUND_HASHTAG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.entity.CommunityBoardImg;
import com.project.sparta.communityBoard.entity.CommunityTag;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.hashtag.service.HashtagService;
import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.like.repository.LikeCommentRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import java.util.ArrayList;
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
public class CommunityBoardRepositoryTest {

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
    User user1 = User
        .userBuilder()
        .email(randomUser)
        .password("1234")
        .nickName("99번째 사용자")
        .age(99)
        .phoneNumber("010-1111-2222")
        .userImageUrl("sdf.jpg")
        .build();
    userRepository.save(user1);
    globalUserId = user1.getId();
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
  @DisplayName("커뮤니티 보드 생성")
  public void communityBoardCreate() {
    //given
    User user1 = userRepository.findById(globalUserId).orElseThrow();

    CommunityBoardRequestDto requestDto2 = CommunityBoardRequestDto
        .builder()
        .chatMemCnt(0)
        .title("첫번째 컨텐츠")
        .contents("첫번쨰 컨텐츠")
        .imgList(imgList)
        .chatStatus("Y")
        .build();

    assertThrows(CustomException.class, ()-> communityBoardService.createCommunityBoard(requestDto, user1));
    assertThrows(NullPointerException.class, ()-> communityBoardService.createCommunityBoard(requestDto2, user1));
  }

  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 수정")
  public void updateCommunityBoard(){
    User user1 = User
        .userBuilder()
        .email(randomUser)
        .password("1234")
        .nickName("99번째 사용자")
        .age(99)
        .phoneNumber("010-1111-2222")
        .userImageUrl("sdf.jpg")
        .build();

    em.persist(user1);

    List taglistOne = Arrays.asList(1L, 2L, 3L, 4L);
    List imgListOne = Arrays.asList("1,2,3,4");
    CommunityBoardRequestDto requestDto = CommunityBoardRequestDto
        .builder()
        .title("첫번째 게시글")
        .chatMemCnt(0)
        .contents("첫번쨰 컨텐츠")
        .tagList(taglistOne)
        .imgList(imgListOne)
        .chatStatus("Y")
        .build();

    communityBoardService.createCommunityBoard(requestDto, user1);

    CommunityBoardRequestDto afterRequestDto = CommunityBoardRequestDto
        .builder()
        .title("에프터 첫번쨰 게시글")
        .chatMemCnt(0)
        .contents("에프터 첫번쨰 컨텐츠")
        .tagList(taglistOne)
        .imgList(imgListOne)
        .chatStatus("Y")
        .build();

    CommunityBoardRequestDto afterRequestDto2 = CommunityBoardRequestDto
        .builder()
        .title("에프터 첫번쨰 게시글")
        .chatMemCnt(0)
        .contents("에프터 첫번쨰 컨텐츠")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();

    CommunityBoardRequestDto afterRequestDto3 = CommunityBoardRequestDto
        .builder()
        .build();

    Long boardIda = 19999L;
    Long boardId =1L;

    assertThrows(CustomException.class, ()-> communityBoardService.updateCommunityBoard(boardIda, afterRequestDto, user1));
    assertThrows(CustomException.class, ()-> communityBoardService.updateCommunityBoard(boardId, afterRequestDto2, user1));
  }

  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 삭제")
  public void deleteCommunityBoard() {
    User user1 =
        User.userBuilder()
            .email(randomUser)
            .password("1234")
            .nickName("99번째 사용자")
            .age(99)
            .phoneNumber("010-1111-2222")
            .userImageUrl("sdf.jpg")
            .build();
    em.persist(user1);
    List taglist = Arrays.asList(1L, 2L, 3L, 4L);
    List imgList = Arrays.asList("1,2,3,4");

    CommunityBoardRequestDto requestDto = CommunityBoardRequestDto.builder()
        .title("첫번째 게시글")
        .chatMemCnt(0)
        .contents("첫번쨰 컨텐츠")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();

    assertThrows(CustomException.class, ()-> communityBoardService.deleteCommunityBoard(199L, user1));
  }

}
