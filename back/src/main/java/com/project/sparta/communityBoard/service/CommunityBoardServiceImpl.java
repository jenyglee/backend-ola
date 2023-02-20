package com.project.sparta.communityBoard.service;

import static com.project.sparta.exception.api.Status.NOT_FOUND_HASHTAG;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.dto.GetMyBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.user.entity.User;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityBoardServiceImpl implements CommunityBoardService {

    private final BoardRepository boardRepository;

    private final HashtagRepository hashtagRepository;

    
    //커뮤니티 게시글 생성
    @Override
    @Transactional
    public void createCommunityBoard(CommunityBoardRequestDto commRequestDto,
        User user) {

        List<Hashtag> newTagList = addHashTag(commRequestDto.getTagList());

        CommunityBoard communityBoard = new CommunityBoard().builder()
            .title(commRequestDto.getTitle())
            .contents(commRequestDto.getContents())
            .tagList(newTagList)
            .chatStatus(commRequestDto.getChatStatus())
            .chatMemCnt(commRequestDto.getChatMemCnt())
            .user(user)
            .build();
        boardRepository.saveAndFlush(communityBoard);
    }

    //커뮤니티 게시글 수정
    @Override
    @Transactional
    public void updateCommunityBoard(Long boardId, CommunityBoardRequestDto commRequestDto, User user) {

        List<Hashtag> newTagList = addHashTag(commRequestDto.getTagList());
        CommunityBoard communityBoard = new CommunityBoard();

        CommunityBoard commBoard = boardRepository.findByIdAndUser_NickName(boardId, user.getNickName())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        communityBoard.updateBoard(commBoard.getTitle(), commBoard.getContents(), newTagList);
        boardRepository.saveAndFlush(communityBoard);
    }

    //커뮤니티 게시글 삭제
    @Override
    @Transactional
    public void deleteCommunityBoard(Long boardId, User user) {
        boardRepository.findByIdAndUser_NickName(boardId, user.getNickName())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
        boardRepository.deleteById(boardId);
    }


    //커뮤니티 게시글 단건 조회(커뮤니티 게시글 + 커뮤 좋아요 + 커뮤니티 댓글 + 커뮤니티 댓글 좋아요)
    @Override
    @Transactional(readOnly = true)
    public CommunityBoardResponseDto getCommunityBoard(Long boardId) {
        CommunityBoardResponseDto communityBoard = boardRepository.getBoard(boardId);
        return communityBoard;
    }

    //커뮤니티 전체조회(커뮤니티 게시글 + 커뮤니티 좋아요 + 페이징)
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<CommunityBoardResponseDto>> getAllCommunityBoard(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommunityBoardResponseDto> allCommunityBoardList =boardRepository.communityAllList(pageRequest);

        List<CommunityBoardResponseDto> content = allCommunityBoardList.getContent();
        long totalCount = allCommunityBoardList.getTotalElements();

        return new PageResponseDto<>(page, totalCount, content);
    }


    //커뮤니티 (제목)검색

    //커뮤니티 (내용)검색

    //커뮤니티 (작성자)검색


    @Override
    @Transactional
    public PageResponseDto<List<GetMyBoardResponseDto>> getMyCommunityBoard(int page, int size,
        User user) {
        return null;
    }




    //새로운 태그 리스트 생성하기 => 커뮤니티 생성, 수정 시 필요함
    public List<Hashtag> addHashTag(List tagDtoList) {

        List<Hashtag> tagList = new ArrayList<>();

        for (int i = 0; i < tagList.size(); i++) {
            Hashtag hashtag = hashtagRepository.findByName(
                    tagDtoList.get(i).toString())
                .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
            tagList.add(hashtag);
        }
        return tagList;
    }

}


//주성님의 고민의 흔적들..
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

//    @Override
//    @Transactional
//    public PageResponseDto<List<GetMyBoardResponseDto>> getMyCommunityBoard(int page, int size,
//        User user) {
//        Sort sort = Sort.by(Sort.Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(page, size, sort);
//        Page<CommunityBoard> boards = boardRepository.findAllByNickName(pageable,
//            user.getNickName());
//
//        List<GetMyBoardResponseDto> getMyBoardResponseDtos = new ArrayList<>();
//        for (CommunityBoard communityBoard : boards) {
//            Long likeCount = likeBoardRepository.countByBoard_Id(communityBoard.getId());
//            GetMyBoardResponseDto getMyBoardResponseDto = GetMyBoardResponseDto.builder()
//                .localDateTime(communityBoard.getCreateAt())
//                .title(communityBoard.getTitle())
//                .likeCount(likeCount)
//                .nickName(communityBoard.getUser().getNickName())
//                .build();
//            getMyBoardResponseDtos.add(getMyBoardResponseDto);
//        }
//        return new PageResponseDto<>(page, boards.getTotalElements(), getMyBoardResponseDtos);
//        return null;
//    }