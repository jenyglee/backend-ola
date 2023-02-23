package com.project.sparta.communityComment.service;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.dto.CommentResponseDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.like.repository.LikeCommentRepository;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService {

  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;
  private final LikeCommentRepository likeCommentRepository;

  @Override
  @Transactional
  public CommentResponseDto createCommunityComments(Long boardId, CommunityRequestDto communityRequestDto,
      User user) {
    CommunityBoard communityBoard = boardRepository.findById(boardId)
        .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

    CommunityComment communityComment = new CommunityComment(communityBoard.getId(), communityRequestDto,
       user);
    commentRepository.saveAndFlush(communityComment);
    return CommentResponseDto.builder()
        .id(communityComment.getId())
        .nickName(communityComment.getNickName())
        .contents(communityComment.getContents())
        .createAt(communityComment.getCreateAt())
        .build();
  }

  @Override
  @Transactional
  public void updateCommunityComments(Long commentId, CommunityRequestDto requestDto,
      User user) {
    CommunityComment communityComment = commentRepository.findByIdAndNickName(commentId,
            user.getNickName()).orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_COMMENT));
    communityComment.updateComment(requestDto.getContents());
    commentRepository.saveAndFlush(communityComment);
  }

  @Override
  @Transactional
  public void deleteCommunityComments(Long commentId, User user) {
    System.out.println("commentId = " + commentId);
    System.out.println("user.getNickName() = " + user.getNickName());
    CommunityComment communityComment = commentRepository.findByIdAndNickName(commentId,
        user.getNickName()).orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_COMMENT));
    likeCommentRepository.deleteLikeAllByInCommentId(commentId);
    //commentRepository.delete(communityComment);
  }

}
