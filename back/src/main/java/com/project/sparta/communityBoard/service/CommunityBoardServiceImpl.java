package com.project.sparta.communityBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityBoardServiceImpl implements CommunityBoardService {

  private final BoardRepository boardRepository;

  @Override
  @Transactional
  public CommunityBoardResponseDto createCommunityBoard(
      CommunityBoardRequestDto communityBoardRequestDto,
      User user) {
    CommunityBoard communityBoard = new CommunityBoard(communityBoardRequestDto, user);
    boardRepository.save(communityBoard);
    CommunityBoardResponseDto communityBoardResponseDto = new CommunityBoardResponseDto(
        communityBoard);
    return communityBoardResponseDto;
  }

  @Override
  @Transactional
  public CommunityBoardResponseDto updateCommunityBoard(
      CommunityRequestDto communityRequestDto,
      User user) {
    CommunityBoard communityBoard = boardRepository.findById(user.getId())
        .orElseThrow(() -> new IllegalArgumentException("수정 할 보드가 없습니다."));
    communityBoard.updateBoard(communityRequestDto.getContents());
    CommunityBoardResponseDto communityBoardResponseDto = new CommunityBoardResponseDto(
        communityBoard);
    return communityBoardResponseDto;
  }

  @Override
  @Transactional
  public CommunityBoardResponseDto getCommunityBoard(User user) {
    CommunityBoard communityBoard = boardRepository.findById(user.getId())
        .orElseThrow(() -> new IllegalArgumentException("수정 할 보드가 없습니다."));
    CommunityBoardResponseDto communityBoardResponseDto = new CommunityBoardResponseDto(
        communityBoard);
    return communityBoardResponseDto;
  }

  @Override
  @Transactional
  public PageResponseDto<List<CommunityBoardResponseDto>> getAllCommunityBoard(int page, int size) {
    Sort sort = Sort.by(Sort.Direction.ASC, "id");
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<CommunityBoard> boards = boardRepository.findAll(pageable);
    List<CommunityBoardResponseDto> CommunityBoardResponseDtoList = boards.getContent()
        .stream()
        .map(CommunityBoardResponseDto::new)
        .collect(Collectors.toList());
    return new PageResponseDto<>(page, boards.getTotalElements(), CommunityBoardResponseDtoList);
  }

  public PageResponseDto<List<CommunityBoardResponseDto>> getMyCommunityBoard(int page, int size,
      User user) {
    Sort sort = Sort.by(Direction.ASC, "id");
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<CommunityBoard> boards = boardRepository.findById(pageable,user.getId());
    List<CommunityBoardResponseDto> CommunityBoardResponseDtoList = boards.getContent()
        .stream()
        .map(CommunityBoardResponseDto::new)
        .collect(Collectors.toList());
    return new PageResponseDto<>(page, boards.getTotalElements(), CommunityBoardResponseDtoList);
  }

  @Override
  @Transactional
  public void deleteCommunityBoard(User user) {
    boardRepository.findById(user.getId())
        .orElseThrow(() -> new IllegalArgumentException("삭제할 보드가 없습니다."));
    boardRepository.deleteById(user.getId());
  }

  @Override
  @Transactional
  public void deleteAllCommunityBoard() {
    boardRepository.deleteAll();
  }
}
