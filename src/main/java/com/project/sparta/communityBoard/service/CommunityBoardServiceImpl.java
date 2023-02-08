package com.project.sparta.communityBoard.service;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.security.UserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityBoardServiceImpl implements CommunityBoardService {

  private final BoardRepository boardRepository;

  @Override
  @Transactional
  public CommunityBoard createCommunityBoard(CommunityBoardRequestDto communityBoardRequestDto,
      String nickName) {
    CommunityBoard communityBoard = new CommunityBoard(communityBoardRequestDto, nickName);
    boardRepository.save(communityBoard);
    return communityBoard;
  }

  @Override
  @Transactional
  public CommunityBoard updateCommunityBoard(Long boardId, CommunityRequestDto communityRequestDto,
      String nickName) {
    CommunityBoard communityBoard = boardRepository.findByNickName(nickName)
        .orElseThrow(() -> new IllegalArgumentException("수정 할 보드가 없습니다."));
    communityBoard.updateBoard(communityRequestDto.getContents());
    return communityBoard;
  }


  @Override
  @Transactional
  public void deleteCommunityBoard(Long boardId) {
    boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("삭제할 보드가 없습니다."));
    boardRepository.deleteById(boardId);
  }

  @Override
  @Transactional
  public void deleteAllCommunityBoard() {
    boardRepository.deleteAll();
  }


}
