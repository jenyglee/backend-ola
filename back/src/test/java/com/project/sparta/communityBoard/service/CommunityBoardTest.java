package com.project.sparta.communityBoard.service;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityBoard.repository.CommunityBoardImgRepository;
import com.project.sparta.communityBoard.repository.CommunityTagRepository;
import com.project.sparta.communityComment.repository.CommentRepository;
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
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class CommunityBoardTest {
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

    //when
    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(requestDto, user1);

    //then
    assertThat(communityBoard.getTitle()).isEqualTo("첫번째 게시글");

  }

  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 수정")
  public void updateCommunityBoard(){
    //given
    User user1 = userRepository.findById(globalUserId).orElseThrow();
    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(requestDto, user1);
    CommunityBoardRequestDto afterRequestDto = CommunityBoardRequestDto
        .builder()
        .title("에프터 첫번쨰 게시글")
        .chatMemCnt(0)
        .contents("에프터 첫번쨰 컨텐츠")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();
    //when
    communityBoardService.updateCommunityBoard(communityBoard.getId(), afterRequestDto, user1);

    //then
    assertThat(communityBoard.getContents()).isEqualTo("에프터 첫번쨰 컨텐츠");
  }
  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 조회")
  public void getCommunityBoard() {

    //given
    User user1 = userRepository.findById(globalUserId).orElseThrow();
    CommunityBoardRequestDto afterRequestDto = CommunityBoardRequestDto
        .builder()
        .title("에프터 첫번쨰 게시글")
        .chatMemCnt(0)
        .contents("에프터 첫번쨰 컨텐츠")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();

    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(afterRequestDto, user1);

    //when
    CommunityBoardOneResponseDto resultCommunityBoard = communityBoardService.getCommunityBoard(communityBoard.getId(), page, size, user1.getNickName());

    //then
    assertThat(resultCommunityBoard.getTitle()).isEqualTo("에프터 첫번쨰 게시글");
  }

  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 전체 조회")
  public void getAllCommunityBoard() {
    //given

    //when
    PageResponseDto<List<CommunityBoardAllResponseDto>> allCommunityBoard = communityBoardService.getAllCommunityBoard(page,size,null,null,null,null,null,null);

    //then
    assertThat(allCommunityBoard.getTotalCount()).isEqualTo(29);
  }
  @Test
  @Transactional
  @DisplayName("커뮤니티 나의 보드 조회")
  public void getMyCommunityBoard() {

    //given
    User user1 = userRepository.findById(globalUserId).orElseThrow();
    CommunityBoardRequestDto afterRequestDto = CommunityBoardRequestDto
        .builder()
        .title("에프터 첫번쨰 게시글")
        .chatMemCnt(0)
        .contents("에프터 첫번쨰 컨텐츠")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();

    communityBoardService.createCommunityBoard(afterRequestDto, user1);

    //when
    PageResponseDto<List<CommunityBoardAllResponseDto>> myCommunityBoard = communityBoardService.getMyCommunityBoard(page, size, user1);

    //then
    assertThat(myCommunityBoard.getTotalCount()).isEqualTo(1);

    }

  @Test
  @Transactional
  @DisplayName("커뮤니티 삭제")
  public void deleteCommunityBoard() {

    //given
    User user1 = userRepository.findById(globalUserId).orElseThrow();
    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(requestDto, user1);

    //when
    communityBoardService.deleteCommunityBoard(communityBoard.getId(),user1);
    PageResponseDto<List<CommunityBoardAllResponseDto>> myCommunityBoard = communityBoardService.getMyCommunityBoard(page, size, user1);

    //then
    assertThat(myCommunityBoard.getTotalCount()).isEqualTo(0);
  }

}