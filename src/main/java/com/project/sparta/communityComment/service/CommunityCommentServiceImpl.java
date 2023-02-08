package com.project.sparta.communityComment.service;

import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityCommentServiceImpl implements CommunityCommentService {
  private final CommunityRepository communityRepository;

  @Override
  @Transactional
  public CommunityComment createComments(Long boardId, CommunityRequestDto communityRequestDto) {
//    Board board = getBoard(boardId);
    //보드 레포짓토리에서 보드가 있는지 확인
    CommunityComment communityComment = new CommunityComment(boardId, communityRequestDto);
    communityRepository.save(communityComment);
    return communityComment;
  }

  public ResponseEntity updateComments(Long boardId, CommunityRequestDto communityRequestDto) {

    //보드 레포짓토리에서 보드가 있는지 확인
    CommunityComment communityComment = new CommunityComment(boardId, communityRequestDto);
    communityRepository.save(communityComment);
    return new ResponseEntity<>(communityComment, HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity deleteComments(Long commentsId) {
    communityRepository.findById(commentsId)
        .orElseThrow(()->new IllegalArgumentException("삭제할 댓글이 없습니다."));
    communityRepository.deleteById(commentsId);
    return new ResponseEntity("댓글 삭제 완료", HttpStatus.OK);
  }

  @Override
  @Transactional
  public ResponseEntity allDeleteComments() {
    communityRepository.deleteAll();
    return new ResponseEntity("댓글 전체 삭제 완료", HttpStatus.OK);
  }

}
