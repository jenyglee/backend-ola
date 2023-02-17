package com.project.sparta.communityBoard.service;

import static com.project.sparta.exception.api.Status.NOT_FOUND_HASHTAG;
import static java.util.Arrays.stream;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.AllCommunityBoardResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.dto.GetMyBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityComment.dto.CommunityWithLikeResponseDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.like.repository.LikeCommentRepository;
import com.project.sparta.user.entity.User;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityBoardServiceImpl implements CommunityBoardService {

    private final BoardRepository boardRepository;
    private final LikeBoardRepository likeBoardRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final HashtagRepository hashtagRepository;

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


    @Override
    @Transactional
    public CommunityBoardResponseDto getCommunityBoard(Long boardId) {
        CommunityBoard communityBoard = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        Long likeCount = likeBoardRepository.countByBoard_Id(communityBoard.getId());//게시글의 좋아요

        List<CommunityWithLikeResponseDto> communityWithLikeResponseDtoList = new ArrayList<>();
        List<CommunityComment> communityCommentList = communityBoard.getCommunityComment();

        for (CommunityComment comment : communityCommentList) {
            Long commentLikeCount = likeCommentRepository.countByComment(comment);
            communityWithLikeResponseDtoList.add(
                new CommunityWithLikeResponseDto(comment, commentLikeCount));
        }

        CommunityBoardResponseDto communityBoardResponseDto = new CommunityBoardResponseDto().builder()
            .title(communityBoard.getTitle())
            .nickName(communityBoard.getUser().getNickName())
            .contents(communityBoard.getContents())
            .id(communityBoard.getId())
            .communityCommentsWithLike(communityWithLikeResponseDtoList)
            .boardLikeCount(likeCount)
            .build();

        return communityBoardResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<AllCommunityBoardResponseDto>> getAllCommunityBoard(int page,
        int size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CommunityBoard> boards = boardRepository.findAll(pageable);

        List<AllCommunityBoardResponseDto> allCommunityBoardResponseDtos = new ArrayList<>();

        for (CommunityBoard communityBoard : boards) {
            Long likeCount = likeBoardRepository.countByBoard(communityBoard);
            allCommunityBoardResponseDtos.add(AllCommunityBoardResponseDto.builder()
                .timestamped(communityBoard.getCreateAt())
                .title(communityBoard.getTitle())
                .nickName(communityBoard.getUser().getNickName())
                .boardLikeCount(likeCount)
                .build());
        }
        return new PageResponseDto<>(page, boards.getTotalElements(),
            allCommunityBoardResponseDtos);
    }


    @Override
    @Transactional
    public PageResponseDto<List<GetMyBoardResponseDto>> getMyCommunityBoard(int page, int size,
        User user) {
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
        return null;
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
    public void deleteCommunityBoard(Long community_board_id, User user) {
        boardRepository.findByIdAndUser_NickName(community_board_id, user.getNickName())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
        boardRepository.deleteById(community_board_id);
    }


    //새로운 태그 리스트 생성하기
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
