package com.project.sparta.communityComment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommunityRepository;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.hashtag.service.HashtagService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class CommentServiceTest {
  @Autowired
  CommunityCommentService commentService;
  @Autowired
  CommunityRepository communityRepository; // 방금추가


  @Test
  public void createCommunityComment() {

    Long boardId = 1L;
    CommunityRequestDto communityRequestDto = new CommunityRequestDto(boardId,"등린이","하이");
    CommunityComment communityComment = commentService.createComments(boardId, communityRequestDto);

    assertThat(communityComment.getUserName()).isEqualTo("등린이");
  }

}
