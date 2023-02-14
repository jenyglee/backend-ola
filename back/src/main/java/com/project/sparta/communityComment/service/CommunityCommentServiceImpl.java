package com.project.sparta.communityComment.service;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.dto.CommunityResponseDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
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
        .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
    CommunityComment communityComment = new CommunityComment(communityBoard.getId(), communityRequestDto,
       user);
    commentRepository.saveAndFlush(communityComment);
    CommunityResponseDto communityResponseDto = new CommunityResponseDto().builder()
        .contents(communityComment.getContents())
        .nickName(communityComment.getNickName())
        .id(communityComment.getId())
        .communityBoardId(boardId)
        .build();
    return communityResponseDto;
  }

  @Override
  @Transactional
  public CommunityResponseDto updateCommunityComments(Long boardId, Long communityCommentId, CommunityRequestDto communityRequestDto,
      User user) {
    boardRepository.findById(boardId)
        .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
    CommunityComment communityComment = commentRepository.findById(communityCommentId)
        .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_COMMENT));
    communityComment.updateComment(communityRequestDto.getContents());
    commentRepository.saveAndFlush(communityComment);
    CommunityResponseDto communityResponseDto = new CommunityResponseDto().builder()
        .contents(communityComment.getContents())
        .build();
    return communityResponseDto;
  }

  @Override
  @Transactional
  public void deleteCommunityComments(Long boardId, Long commentsId,User user) {
    boardRepository.findById(boardId)
        .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

    CommunityComment communityComment = commentRepository.findById(commentsId)
        .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_COMMENT));

    if(communityComment.getNickName() != user.getNickName())
    {
      new CustomException(Status.INVALID_USER);
    }

    commentRepository.deleteById(commentsId);
  }

}
