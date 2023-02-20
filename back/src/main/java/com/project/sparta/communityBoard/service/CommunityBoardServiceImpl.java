package com.project.sparta.communityBoard.service;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import static com.project.sparta.exception.api.Status.NOT_FOUND_HASHTAG;

import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.GetMyBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;

import com.project.sparta.communityBoard.repository.CommunityTagRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.communityBoard.entity.CommunityTag;
import com.project.sparta.user.entity.User;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityBoardServiceImpl implements CommunityBoardService {

    private final BoardRepository boardRepository;

    private final HashtagRepository hashtagRepository;
    private final CommunityTagRepository communityTagRepository;

    
    //커뮤니티 게시글 생성
    @Override
    @Transactional
    public void createCommunityBoard(CommunityBoardRequestDto commRequestDto,
        User user) {

//        List<Hashtag> newTagList = addHashTag(commRequestDto.getTagList());


        CommunityBoard communityBoard = new CommunityBoard().builder()
            .title(commRequestDto.getTitle())
            .contents(commRequestDto.getContents())
//            .tagList(newTagList)
            .chatStatus(commRequestDto.getChatStatus())
            .chatMemCnt(commRequestDto.getChatMemCnt())
            .user(user)
            .build();
        boardRepository.saveAndFlush(communityBoard);


        // 저장된 보드에 태그 넣기
        List<CommunityTag> communityTagList = new ArrayList<>();
        for (Long tagNum : commRequestDto.getTagList()) {
            Hashtag hashtag = hashtagRepository.findById(tagNum)
                .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
            CommunityTag communityTag = new CommunityTag(communityBoard, hashtag);
            communityTagRepository.save(communityTag);
            communityTagList.add(communityTag);
        }
        communityBoard.updateCommunityTag(communityTagList);
    }

    //커뮤니티 게시글 수정
    @Override
    @Transactional
    public void updateCommunityBoard(Long boardId, CommunityBoardRequestDto requestDto, User user) {
//        CommunityBoard commBoard = boardRepository.findByIdAndUser_NickName(boardId, user.getNickName())
//            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
////        List<Hashtag> newTagList = addHashTag(requestDto.getTagList());
//        List<CommunityTag> communityTagList = new ArrayList<>();
//        for (Long tagNum : requestDto.getTagList()) {
//            Hashtag hashtag = hashtagRepository.findById(tagNum)
//                .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
//            CommunityTag communityTag = new CommunityTag(commBoard, hashtag);
//            communityTagRepository.save(communityTag);
//            communityTagList.add(communityTag);
//        }
//
//        CommunityBoard communityBoard = new CommunityBoard();
//
//
//
//        communityBoard.updateBoard(requestDto.getTitle(), requestDto.getContents(), communityTagList);
//        boardRepository.saveAndFlush(communityBoard);
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
    public CommunityBoardOneResponseDto getCommunityBoard(Long boardId) {
        CommunityBoardOneResponseDto communityBoard = boardRepository.getBoard(boardId);
        return communityBoard;
    }

    //커뮤니티 전체조회(커뮤니티 게시글 + 커뮤니티 좋아요 + 페이징)
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<CommunityBoardAllResponseDto>> getAllCommunityBoard(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        QueryResults<CommunityBoardAllResponseDto> allCommunityBoardList =boardRepository.communityAllList(pageRequest);

        List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getResults();
        System.out.println("content.size() = " + content.size());

        long totalCount = allCommunityBoardList.getTotal();

        return new PageResponseDto<>(page, totalCount, content);
        //        List<CommunityBoardOneResponseDto> content = allCommunityBoardList.getContent();
        //        long totalCount = allCommunityBoardList.getTotalElements();
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

    // (어드민) 커뮤니티 수정
    @Override
    @Transactional
    public void adminUpdateCommunityBoard(Long boardId, CommunityBoardRequestDto requestDto) {
//        CommunityBoard commBoard = boardRepository.findById(boardId)
//                .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
////        List<Hashtag> newTagList = addHashTag(requestDto.getTagList());
//        List<CommunityTag> communityTagList = new ArrayList<>();
//        for (Long tagNum : requestDto.getTagList()) {
//            Hashtag hashtag = hashtagRepository.findById(tagNum)
//                .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
//            CommunityTag communityTag = new CommunityTag(commBoard, hashtag);
//            communityTagRepository.save(communityTag);
//            communityTagList.add(communityTag);
//        }
//        commBoard.updateBoard(requestDto.getTitle(), requestDto.getContents(), communityTagList);
//        // boardRepository.saveAndFlush(communityBoard);
    }

    // (어드민) 커뮤니티 삭제
    @Override
    @Transactional
    public void adminDeleteCommunityBoard(Long boardId) {
        CommunityBoard commBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
        boardRepository.delete(commBoard);
    }


    //새로운 태그 리스트 생성하기 => 커뮤니티 생성, 수정 시 필요함
    public List<Hashtag> addHashTag(List<Long> tagIdList) {

        List<Hashtag> tagList = new ArrayList<>();

        for (int i = 0; i < tagList.size(); i++) {
            Hashtag hashtag = hashtagRepository.findByName(
                    tagIdList.get(i).toString())
                .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
            tagList.add(hashtag);
        }
        return tagList;
    }


}


//주성님의 고민의 흔적들..
//  @Override
//  @Transactional
//  public PageResponseDto<List<CommunityBoardOneResponseDto>> getMyCommunityBoard(int page, int size,
//      User user) {
//    Sort sort = Sort.by(Direction.ASC, "id");
//    Pageable pageable = PageRequest.of(page, size, sort);
//    Page<CommunityBoard> boards = boardRepository.findById(pageable,user.getId());
//    List<CommunityBoardOneResponseDto> CommunityBoardOneResponseDtoList = boards.getContent()
//        .stream()
//        .map(CommunityBoardOneResponseDto::new)
//        .collect(Collectors.toList());
//    return new PageResponseDto<>(page, boards.getTotalElements(), CommunityBoardOneResponseDtoList);
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