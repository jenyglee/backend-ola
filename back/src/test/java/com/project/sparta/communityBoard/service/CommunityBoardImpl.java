package com.project.sparta.communityBoard.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityBoard.repository.BoardRepositoryImpl;
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
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
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
  private UserRepository userRepository;

@BeforeEach
  public User user(){
    User user1 = new User("user1@naver.com", "1234", "이재원", 10, "010-1234-1234", "sdf.jpg");
    userRepository.saveAndFlush(user1);
    return user1;
  }
  @BeforeEach
  public CommunityBoardRequestDto communityRequest(){
    List taglist = Arrays.asList(1, 2, 3, 4);
    List imgList = Arrays.asList("1,2,3,4");

    CommunityBoardRequestDto requestDto = new CommunityBoardRequestDto("첫번쨰 컨텐츠", "첫번째 게시글", "Y", 0,
        taglist, imgList);
    return requestDto;
  }
  @Test
//  @Transactional
  @DisplayName("커뮤니티 보드 생성")
  public void communityBoard() {
  User user1 = User();


    CommunityBoard communityBoard = communityBoardService.createCommunityBoard(requestDto, user1);
    boardRepository.saveAndFlush(communityBoard);
    assertThat(communityBoard.getChatStatus()).isEqualTo("Y");
  }

}