package com.project.sparta.communityBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
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
  public CommunityBoardResponseDto createCommunityBoard(CommunityBoardRequestDto communityBoardRequestDto,
      User user) {
    CommunityBoard communityBoard = new CommunityBoard(communityBoardRequestDto, user);
    boardRepository.saveAndFlush(communityBoard);
    CommunityBoardResponseDto communityBoardResponseDto = new CommunityBoardResponseDto(
        communityBoard);
    return communityBoardResponseDto;
  }

  @Override
  @Transactional
  public CommunityBoardResponseDto updateCommunityBoard(Long community_board_id, CommunityBoardRequestDto communityBoardRequestDto,
      User user) {
    CommunityBoard communityBoard = boardRepository.findById(community_board_id)
        .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
    communityBoard.updateBoard(communityBoardRequestDto);
    boardRepository.saveAndFlush(communityBoard);
    CommunityBoardResponseDto communityBoardResponseDto = new CommunityBoardResponseDto(
        communityBoard);
    return communityBoardResponseDto;
  }

  @Override
  @Transactional
  public CommunityBoardResponseDto getCommunityBoard(Long communityBoardId) {
    CommunityBoard communityBoard = boardRepository.findById(communityBoardId)
        .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
    CommunityBoardResponseDto communityBoardResponseDto = new CommunityBoardResponseDto(
        communityBoard);
    return communityBoardResponseDto;
  }

  @Override
  @Transactional
  public List<CommunityBoardResponseDto> getAllCommunityBoard(int page, int size) {
    Sort sort = Sort.by(Sort.Direction.ASC, "id");
    Pageable pageable = PageRequest.of(page, size, sort);

    Page<CommunityBoard> boards = boardRepository.findAll(pageable);
    List<CommunityBoardResponseDto> CommunityBoardResponseDtoList = boards.getContent()
        .stream()
        .map(CommunityBoardResponseDto::new)
        .collect(Collectors.toList());
    return CommunityBoardResponseDtoList;
  }
  @Override
  @Transactional
  public List<CommunityBoardResponseDto> getMyCommunityBoard(int page, int size, User user) {
    Sort sort = Sort.by(Direction.ASC, "id");
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<CommunityBoard> boards = boardRepository.findAllById(pageable,user.getId());
    List<CommunityBoardResponseDto> CommunityBoardResponseDtoList = boards.getContent()
        .stream()
        .map(CommunityBoardResponseDto::new)
        .collect(Collectors.toList());
    return CommunityBoardResponseDtoList;
  }

//  @Override
//  @Transactional
//  public PageResponseDto<List<CommunityBoardResponseDto>> getMyCommunityBoard(int page, int size,
//      User user) {
//    Sort sort = Sort.by(Direction.ASC, "id");
//    Pageable pageable = PageRequest.of(page, size, sort);
//    Page<CommunityBoard> boards = boardRepository.findById(pageable,user.getId());
//    List<CommunityBoardResponseDto> CommunityBoardResponseDtoList = boards.getContent()
//        .stream()
//        .map(CommunityBoardResponseDto::new)
//        .collect(Collectors.toList());
//    return new PageResponseDto<>(page, boards.getTotalElements(), CommunityBoardResponseDtoList);
//  }

  @Override
  @Transactional
  public void deleteCommunityBoard(Long community_board_id) {
    boardRepository.findById(community_board_id)
        .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
    boardRepository.deleteById(community_board_id);
  }

}
