package com.project.sparta.communityComment.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
public class CommentServiceTest {
  // @Autowired
  // CommunityCommentService commentService;
  // @Autowired
  // CommentRepository commentRepository; // 방금추가
  //
  // @Autowired
  // CommunityBoardService communityBoardService;
  // @Autowired
  // BoardRepository boardRepository; // 방금추가
  // @Autowired
  // UserRepository userRepository;
  //
  // @Test
  // @Name("보드 생성 테스트")
  // public void createCommunityBoard() {
  //   User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
  //   userRepository.saveAndFlush(user1);
  //
  //   CommunityBoardRequestDto communityBoardRequestDto = new CommunityBoardRequestDto("보드 하이", "첫번쨰 보드");
  //   CommunityBoardOneResponseDto CommunityBoardOneResponseDto = communityBoardService.createCommunityBoard(communityBoardRequestDto,user1);
  //
  //   assertThat(CommunityBoardOneResponseDto.getNickName()).isEqualTo("이재원");
  //   System.out.println(CommunityBoardOneResponseDto);
  // }
  //
  //
  // @Test
  // @Name("댓글 생성 테스트")
  // public void createCommunityComment() {
  //   //    User user1 = new User("1234", "이재원", 10, "010-1234-1234", "user1@naver.com", UserRoleEnum.USER, "user1.jpg",USER_REGISTERED);
  //   User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
  //   //    User user2 = new User("1234", "한세인", 20, "010-1234-1235", "user2@naver.com", UserRoleEnum.USER, "user2.jpg",USER_REGISTERED);
  //   userRepository.saveAndFlush(user1);
  //   CommunityBoardRequestDto communityBoardRequestDto = new CommunityBoardRequestDto("보드 하이", "첫번쨰 보드");
  //   CommunityBoardOneResponseDto CommunityBoardOneResponseDto = communityBoardService.createCommunityBoard(communityBoardRequestDto,user1);
  //
  //   CommunityRequestDto communityRequestDto = new CommunityRequestDto("하이");
  //   CommunityResponseDto communityResponseDto = commentService.createCommunityComments(CommunityBoardOneResponseDto.getId(), communityRequestDto,
  //       user1);
  //
  //
  //   assertThat(communityResponseDto.getNickName()).isEqualTo("이재원");
  //   System.out.println(communityResponseDto);
  // }

}
