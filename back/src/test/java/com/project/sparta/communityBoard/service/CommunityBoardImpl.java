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
@Rollback(value = false)
@Transactional
public class CommunityBoardImpl {

  @Autowired
  BoardRepository boardRepository;
  @Autowired
  HashtagRepository hashtagRepository;
  @Autowired
  CommunityTagRepository communityTagRepository;
  @Autowired
  CommunityBoardImgRepository communityBoardImgRepository;
  @Autowired
  CommentRepository commentRepository;
  @Autowired
  LikeCommentRepository likeCommentRepository;
  @Autowired
  LikeBoardRepository likeBoardRepository;
  @Autowired
  BoardRepositoryImpl boardRepositoryImpl;
  @Autowired
  CommunityBoardService communityBoardService;
  @Autowired
  UserRepository userRepository;
  @Autowired
  EntityManager em;
  @Test
  @DisplayName("커뮤니티 보드 생성")
  public void communityBoardCreate() {
    User user1 = new User("user100@naver.com", "1234", "이재원", 10, "010-1234-1234", "sdf.jpg");
    em.persist(user1);
    List taglist = Arrays.asList(1L, 2L, 3L, 4L);
    List imgList = Arrays.asList("1,2,3,4");
    CommunityBoardRequestDto requestDto = new CommunityBoardRequestDto("첫번쨰 컨텐츠", "첫번째 게시글", "Y", 0,
        taglist, imgList);

    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(requestDto, user1);
    boardRepository.saveAndFlush(communityBoard);

    assertThat(communityBoard.getChatStatus()).isEqualTo("Y");

  }

  @Test
  @DisplayName("커뮤니티 보드 수정")
  public void updateCommunityBoard(){
    User user1 = new User("user9@naver.com", "1234", "이재원", 10, "010-1234-1234", "sdf.jpg");
//    em.persist(user1);
    List taglist = Arrays.asList(1L, 2L, 3L, 4L);
    List imgList = Arrays.asList("1,2,3,4");
    CommunityBoardRequestDto requestDto = new CommunityBoardRequestDto("convert 첫번쨰 컨텐츠", "첫번째 게시글", "Y", 0,
        taglist, imgList);

    Long boardId = 2215L;

    CommunityBoard community = boardRepository.findByIdAndUser_NickName(boardId,
        user1.getNickName()) .orElseThrow(() -> new CustomException(NOT_FOUND_COMMUNITY_BOARD));

//    communityTagRepository.deleteTagAllByBoardId(boardId);
    List<CommunityTag> communityTags = new ArrayList<>();
    for (Long tag : requestDto.getTagList()) {
      Hashtag hashtag = hashtagRepository.findById(tag)
          .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
      CommunityTag communityTag = new CommunityTag(community, hashtag);
      communityTagRepository.save(communityTag);
      communityTags.add(communityTag);
    }

    // 커뮤니티에 있는 이미지를 전부 지우고 새로 저장한다.
//    communityBoardImgRepository.deleteImgAllByBoardId(boardId);
    List<CommunityBoardImg> communityImgList = new ArrayList<>();
    for (String imgUrl : requestDto.getImgList()) {
      CommunityBoardImg communityImg = new CommunityBoardImg(imgUrl, community);
      communityBoardImgRepository.save(communityImg);
      communityImgList.add(communityImg);
    }

    community.updateBoard(
        requestDto.getTitle(),
        requestDto.getContents(),
        communityTags,
        communityImgList,
        requestDto.getChatStatus(),
        requestDto.getChatMemCnt()
    );
    assertThat(community.getContents()).isEqualTo("convert 첫번쨰 컨텐츠");
  }
  @Test
  @DisplayName("커뮤니티 보드 조회")
  public void getCommunityBoard() {
    PageRequest pageRequest = PageRequest.of(0, 10);
    Long boardId = 2215L;
    String nickname = "이재원";
    CommunityBoardOneResponseDto communityBoard = boardRepository.getBoard(boardId, pageRequest, nickname);
    assertThat(communityBoard.getTitle()).isEqualTo("첫번째 게시글");
  }

  @Test
  @DisplayName("커뮤니티 보드 전체 조회")
  public void getAllCommunityBoard() {
    // 1. 검색 조건을 객체로 저장
    CommunitySearchCondition searchCondition = new CommunitySearchCondition("",
        "", "", 13L, "Y", "likeDesc");

    // 2. 검색조건을 포함하여 전체조회
    PageRequest pageRequest = PageRequest.of(0, 10);
    Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.communityAllList(
        searchCondition, pageRequest);
    //3. 결과를 반환
    List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getContent();
    long totalCount = allCommunityBoardList.getTotalElements();
    assertThat(allCommunityBoardList.getTotalElements()).isEqualTo(totalCount);

  }

}