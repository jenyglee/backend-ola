package com.project.sparta.communityComment.service;

import static com.project.sparta.communityComment.entity.QCommunityComment.communityComment;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.security.UserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService {

  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;

  @Override
  @Transactional
  public CommunityComment createCommunityComments(Long boardId, CommunityRequestDto communityRequestDto,
      String nickName) {
    boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("댓글 달 보드가 없습니다."));
    CommunityComment communityComment = new CommunityComment(boardId, communityRequestDto,
        nickName);
    commentRepository.save(communityComment);
    return communityComment;
  }

  @Override
  @Transactional
  public CommunityComment updateCommunityComments(Long boardId, CommunityRequestDto communityRequestDto,
      String nickName) {

    boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("댓글 달 보드가 없습니다."));
    CommunityComment communityComment = commentRepository.findByNickName(nickName)
        .orElseThrow(() -> new IllegalArgumentException("수정 할 댓글이 없습니다."));
    communityComment.updateComment(communityRequestDto.getContents());
    commentRepository.save(communityComment);
    return communityComment;
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
