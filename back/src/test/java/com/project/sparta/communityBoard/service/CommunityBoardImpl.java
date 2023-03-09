package com.project.sparta.communityBoard.service;

import static com.project.sparta.exception.api.Status.NOT_FOUND_COMMUNITY_BOARD;
import static com.project.sparta.exception.api.Status.NOT_FOUND_HASHTAG;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunitySearchCondition;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.entity.CommunityBoardImg;
import com.project.sparta.communityBoard.entity.CommunityTag;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityBoard.repository.BoardRepositoryImpl;
import com.project.sparta.communityBoard.repository.CommunityBoardImgRepository;
import com.project.sparta.communityBoard.repository.CommunityTagRepository;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.like.repository.LikeCommentRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class CommunityBoardImpl {

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
  EntityManager em;

  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 생성")
  public void communityBoardCreate() {
    User user1 = User
        .userBuilder()
        .email("user99@naver.com")
        .password("1234")
        .nickName("99번째 사용자")
        .age(99)
        .phoneNumber("010-1111-2222")
        .userImageUrl("sdf.jpg")
        .build();
    em.persist(user1);

    List taglist = Arrays.asList(1L, 2L, 3L, 4L);
    List imgList = Arrays.asList("1,2,3,4");

    CommunityBoardRequestDto requestDto = CommunityBoardRequestDto
        .builder()
        .title("첫번째 게시글")
        .chatMemCnt(0)
        .contents("첫번쨰 컨텐츠")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();

    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(requestDto, user1);
    assertThat(communityBoard.getTitle()).isEqualTo("첫번째 게시글");

  }

  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 수정")
  public void updateCommunityBoard(){
    User user1 = User
        .userBuilder()
        .email("user99@naver.com")
        .password("1234")
        .nickName("99번째 사용자")
        .age(99)
        .phoneNumber("010-1111-2222")
        .userImageUrl("sdf.jpg")
        .build();
    em.persist(user1);
    List taglist = Arrays.asList(1L, 2L, 3L, 4L);
    List imgList = Arrays.asList("1,2,3,4");

    CommunityBoardRequestDto requestDto = CommunityBoardRequestDto
        .builder()
        .title("첫번째 게시글")
        .chatMemCnt(0)
        .contents("첫번쨰 컨텐츠")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();
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

    Long boardId = communityBoard.getId();

    CommunityBoard community = boardRepository.findByIdAndUser_NickName(boardId,
        user1.getNickName()) .orElseThrow(() -> new CustomException(NOT_FOUND_COMMUNITY_BOARD));

    communityTagRepository.deleteTagAllByBoardId(boardId);
    List<CommunityTag> communityTags = new ArrayList<>();
    for (Long tag : requestDto.getTagList()) {
      Hashtag hashtag = hashtagRepository.findById(tag)
          .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
      CommunityTag communityTag = new CommunityTag(community, hashtag);
      communityTagRepository.save(communityTag);
      communityTags.add(communityTag);
    }

    // 커뮤니티에 있는 이미지를 전부 지우고 새로 저장한다.
    communityBoardImgRepository.deleteImgAllByBoardId(boardId);
    List<CommunityBoardImg> communityImgList = new ArrayList<>();
    for (String imgUrl : requestDto.getImgList()) {
      CommunityBoardImg communityImg = new CommunityBoardImg(imgUrl, community);
      communityBoardImgRepository.save(communityImg);
      communityImgList.add(communityImg);
    }

    community.updateBoard(
        afterRequestDto.getTitle(),
        afterRequestDto.getContents(),
        communityTags,
        communityImgList,
        afterRequestDto.getChatStatus(),
        afterRequestDto.getChatMemCnt()
    );
    assertThat(community.getId()).isEqualTo(boardId);
    assertThat(community.getContents()).isEqualTo("에프터 첫번쨰 컨텐츠");
  }
  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 조회")
  public void getCommunityBoard() {
    PageRequest pageRequest = PageRequest.of(0, 10);
    User user1 = User
        .userBuilder()
        .email("user99@naver.com")
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
    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(requestDto, user1);

    Long boardId = communityBoard.getId();
    String nickname = "이재원";
    CommunityBoardOneResponseDto resultCommunityBoard = boardRepository.getBoard(boardId, pageRequest, nickname);
    assertThat(resultCommunityBoard.getTitle()).isEqualTo("첫번째 게시글");
  }

  @Test
  @Transactional
  @DisplayName("커뮤니티 보드 전체 조회")
  public void getAllCommunityBoard() {

    User user1 =
        User.userBuilder()
            .email("user99@naver.com")
            .password("1234")
            .nickName("99번째 사용자")
            .age(99)
            .phoneNumber("010-1111-2222")
            .userImageUrl("sdf.jpg")
            .build();
    em.persist(user1);
    List taglist = Arrays.asList(27L,1L);
    List imgList = Arrays.asList("1,2,3,4");

    CommunityBoardRequestDto requestDto = CommunityBoardRequestDto
        .builder()
        .title("첫번째 게시글1")
        .chatMemCnt(0)
        .contents("첫번쨰 컨텐츠1")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();

    CommunityBoardRequestDto requestDto2 = CommunityBoardRequestDto
        .builder()
        .title("첫번째 게시글2")
        .chatMemCnt(0)
        .contents("첫번쨰 컨텐츠2")
        .tagList(taglist)
        .imgList(imgList)
        .chatStatus("Y")
        .build();
    communityBoardService.createCommunityBoard(requestDto, user1);
    communityBoardService.createCommunityBoard(requestDto2, user1);

    // 1. 검색 조건을 객체로 저장
    // 1. 검색 조건을 객체로 저장

    CommunitySearchCondition searchCondition = CommunitySearchCondition
        .builder()
        .title("")
        .nickname("")
        .contents("")
        .hashtagId(27L)
        .chatStatus("Y")
        .sort("likeDesc")
        .build();

    // 2. 검색조건을 포함하여 전체조회
    PageRequest pageRequest = PageRequest.of(0, 10);
    Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.communityAllList(
        searchCondition, pageRequest);
    //3. 결과를 반환
    List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getContent();
    long totalCount = allCommunityBoardList.getTotalElements();
    assertThat(totalCount).isEqualTo(2);

  }
  @Test
  @Transactional
  @DisplayName("커뮤니티 나의 보드 조회")
  public void getMyCommunityBoard() {

    User user1 =
        User.userBuilder()
            .email("user99@naver.com")
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

    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(requestDto, user1);

    PageRequest pageRequest = PageRequest.of(0, 10);
    Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.communityMyList(pageRequest, user1.getId());

    //3. 결과를 반환
    List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getContent();
    long totalCount = allCommunityBoardList.getTotalElements();

    assertThat(totalCount).isEqualTo(1);

    }

  @Test
  @Transactional
  @DisplayName("커뮤니티 삭제")
  public void deleteCommunityBoard() {
    User user1 =
        User.userBuilder()
            .email("user99@naver.com")
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

    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(requestDto, user1);
    Long boardId = communityBoard.getId();
    // 1. Comment Id에 해당하는 Like를 전부 지운다.
    List<Long> commentIds = commentRepository.findIdsByCommunityBoardId(boardId);
    likeCommentRepository.deleteLikeAllByInCommentIds(commentIds);

    // 2. Comment를 전부 지운다.
    commentRepository.deleteCommentAllByInBoardId(boardId);

    // 3. Tag, Img, CommunityLike를 전부 지운다.
    communityTagRepository.deleteTagAllByBoardId(communityBoard.getId());
    communityBoardImgRepository.deleteImgAllByBoardId(communityBoard.getId());
    likeBoardRepository.deleteLikeAllByInBoardId(boardId);

    // 4. 모든 연관관계를 지웠으니 이제 게시글을 지운다.
    boardRepository.deleteById(communityBoard.getId());

    communityBoard.getId();
  }

}