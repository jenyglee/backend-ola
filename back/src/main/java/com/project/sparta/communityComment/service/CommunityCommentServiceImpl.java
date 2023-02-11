package com.project.sparta.communityComment.service;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.dto.CommunityResponseDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService {

  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;

  @Override
  @Transactional
  public CommunityResponseDto createCommunityComments(Long boardId, CommunityRequestDto communityRequestDto,
      User user) {
    CommunityBoard communityBoard = boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("댓글 달 보드가 없습니다."));
    CommunityComment communityComment = new CommunityComment(communityBoard.getId(), communityRequestDto,
       user);
    commentRepository.saveAndFlush(communityComment);
    CommunityResponseDto communityResponseDto = new CommunityResponseDto(communityComment);
    return communityResponseDto;
  }

  @Override
  @Transactional
  public CommunityResponseDto updateCommunityComments(Long boardId, CommunityRequestDto communityRequestDto,
      User user) {
    boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("댓글 달 보드가 없습니다."));
    CommunityComment communityComment = commentRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("수정 할 댓글이 없습니다."));
    communityComment.updateComment(communityRequestDto.getContents());
    commentRepository.save(communityComment);
    CommunityResponseDto communityResponseDto = new CommunityResponseDto(communityComment);
    return communityResponseDto;
  }
  @Override
  @Transactional
  public void deleteCommunityComments(Long commentsId) {
    commentRepository.findById(commentsId)
        .orElseThrow(() -> new IllegalArgumentException("삭제할 댓글이 없습니다."));
    commentRepository.deleteById(commentsId);
  }

  @Override
  @Transactional
  public void allDeleteCommunityComments() {
    commentRepository.deleteAll();
  }

}
