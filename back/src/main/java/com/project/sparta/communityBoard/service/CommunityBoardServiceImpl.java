package com.project.sparta.communityBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;

import static com.project.sparta.exception.api.Status.NOT_FOUND_HASHTAG;

import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.CommunitySearchCondition;
import com.project.sparta.communityBoard.dto.GetMyBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.entity.CommunityBoardImg;
import com.project.sparta.communityBoard.repository.BoardRepository;

import com.project.sparta.communityBoard.repository.BoardRepositoryImpl;
import com.project.sparta.communityBoard.repository.CommunityBoardImgRepository;
import com.project.sparta.communityBoard.repository.CommunityTagRepository;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.communityBoard.entity.CommunityTag;
import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.like.repository.LikeCommentRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserGradeEnum.Authority;
import com.project.sparta.user.repository.UserRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
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
    private final CommunityBoardImgRepository communityBoardImgRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final LikeBoardRepository likeBoardRepository;

    private final BoardRepositoryImpl boardRepositoryImpl;


    long chatCount=0;
    long boardCount = 0;
    private final UserRepository userRepository;

    //커뮤니티 생성
    @Override
    @Transactional
    public CommunityBoard createCommunityBoard(CommunityBoardRequestDto requestDto,
        User user) {
        CommunityBoard communityBoard = new CommunityBoard().builder()
            .title(requestDto.getTitle())
            .contents(requestDto.getContents())
            .chatStatus(requestDto.getChatStatus())
            .chatMemCnt(requestDto.getChatMemCnt())
            .user(user)
            .godResponse(0)
            .maniaResponse(0)
            .build();

        CommunityBoard board = boardRepository.saveAndFlush(communityBoard);

        // 저장된 보드에 태그 넣기 TODO 따로 메서드로 추출하기
        List<CommunityTag> communityTagList = new ArrayList<>();
        for (Long tagNum : requestDto.getTagList()) {
            Hashtag hashtag = hashtagRepository.findById(tagNum)
                .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
            CommunityTag communityTag = new CommunityTag(communityBoard, hashtag);
            communityTagRepository.save(communityTag);
            communityTagList.add(communityTag);
        }
        communityBoard.updateCommunityTag(communityTagList);

        // 저장된 보드에 이미지 넣기 TODO 따로 메서드로 추출하기
        List<CommunityBoardImg> communityImgList = new ArrayList<>();
        for (String imgUrl : requestDto.getImgList()) {
            CommunityBoardImg communityImg = new CommunityBoardImg(imgUrl, communityBoard);
            communityBoardImgRepository.save(communityImg);
            communityImgList.add(communityImg);
        }
        communityBoard.updateCommunityImg(communityImgList);

        // 자동 등업
        autoGradeUp(chatCount,boardCount,user,communityBoard);

        return board;//채팅방 생성을 위한 return
    }

    @Override
    public void autoGradeUp(Long chatCount,Long boardCount,User user , CommunityBoard communityBoard){
        if(boardCount<2){
            boardCount = boardRepositoryImpl.getBoardCount(user.getId());
        }
        if(boardCount ==2){
            // 등산매니아 업그레이드
            communityBoard.set_maniaResponse(1);
            System.out.println("등산 매니아 업그레이드!!!!!!!!!!!!!!!!!!!!!!!!!!");
            user.changeGrade(UserGradeEnum.MOUNTAIN_MANIA);
            System.out.println("등급 : " + user.getGradeEnum());
            userRepository.saveAndFlush(user);
        }
        if(boardCount>=2&& chatCount<=2){
            // 1. 검색조건을 포함하여 전체조회
            PageRequest pageRequest = PageRequest.of(0, 5);
            Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.myChatBoardList(user.getId(), pageRequest);
            //2. 결과를 반환
            chatCount = allCommunityBoardList.getTotalElements();
        }
        if(chatCount ==2){
            //산신령 업그레이드
            System.out.println("산신령 업그레이드!!!!!!!!!!!!!!!!!!!!!!!!!!");
            communityBoard.set_godResponse(1);
            user.changeGrade(UserGradeEnum.MOUNTAIN_GOD);
            System.out.println("등급 : " + user.getGradeEnum());
            userRepository.saveAndFlush(user);
        }
    }

    //커뮤니티 수정
    @Override
    @Transactional
    public void updateCommunityBoard(Long boardId, CommunityBoardRequestDto requestDto, User user) {
        CommunityBoard community = boardRepository.findByIdAndUser_NickName(boardId,
                user.getNickName())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        // 커뮤니티에 있는 태그를 전부 지우고 새로 저장한다.
        communityTagRepository.deleteTagAllByBoardId(boardId);
        List<CommunityTag> communityTags = new ArrayList<>();
        for (Long tag : requestDto.getTagList()) {
            Hashtag hashtag = hashtagRepository.findById(tag)
                .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
            CommunityTag communityTag = new CommunityTag(community, hashtag);
            communityTagRepository.save(communityTag);
            communityTags.add(communityTag);
        }

        // 커뮤니티에 있는 이미지를 전부 지우고 새로 저장한다.
        communityBoardImgRepository.deleteImgAllByBoardId(boardId);
        List<CommunityBoardImg> communityImgList = new ArrayList<>();
        for (String imgUrl : requestDto.getImgList()) {
            CommunityBoardImg communityImg = new CommunityBoardImg(imgUrl, community);
            communityBoardImgRepository.save(communityImg);
            communityImgList.add(communityImg);
        }

        community.updateBoard(
            requestDto.getTitle(),
            requestDto.getContents(),
            communityTags,
            communityImgList,
            requestDto.getChatStatus(),
            requestDto.getChatMemCnt()
        );
    }

    //커뮤니티 삭제
    @Override
    @Transactional
    public void deleteCommunityBoard(Long boardId, User user) {
        CommunityBoard communityBoard = boardRepository.findByIdAndUser_NickName(boardId,
                user.getNickName())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        // 1. Comment Id에 해당하는 Like를 전부 지운다.
        List<Long> commentIds = commentRepository.findIdsByCommunityBoardId(boardId);
        likeCommentRepository.deleteLikeAllByInCommentIds(commentIds);

        // 2. Comment를 전부 지운다.
        commentRepository.deleteCommentAllByInBoardId(boardId);

        // 3. Tag, Img, CommunityLike를 전부 지운다.
        communityTagRepository.deleteTagAllByBoardId(communityBoard.getId());
        communityBoardImgRepository.deleteImgAllByBoardId(communityBoard.getId());
        likeBoardRepository.deleteLikeAllByInBoardId(boardId);

        // 4. 모든 연관관계를 지웠으니 이제 게시글을 지운다.
        boardRepository.deleteById(communityBoard.getId());
    }


    //커뮤니티 단건 조회(커뮤니티 게시글 + 커뮤 좋아요 + 커뮤니티 댓글 + 커뮤니티 댓글 좋아요)
    @Override
    @Transactional(readOnly = true)
    public CommunityBoardOneResponseDto getCommunityBoard(Long boardId, int commentPage, int commentSize, String nickname) {
        PageRequest pageRequest = PageRequest.of(commentPage, commentSize);

        CommunityBoardOneResponseDto communityBoard = boardRepository.getBoard(boardId, pageRequest, nickname);

        return communityBoard;
    }


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value="getallcommunity")
    public PageResponseDto<List<CommunityBoardAllResponseDto>> getCacheAllCommunityBoard(int page,
        int size, String titleCond, String contentsCond, String nicknameCond, Long hashtagId, String chatStatus, String sort) {
        // 1. 검색 조건을 객체로 저장
        CommunitySearchCondition searchCondition = new CommunitySearchCondition(titleCond,
            contentsCond, nicknameCond, hashtagId, chatStatus, sort);

        // 2. 검색조건을 포함하여 전체조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.communityAllList(
            searchCondition, pageRequest);
        //3. 결과를 반환
        List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getContent();
        long totalCount = allCommunityBoardList.getTotalElements();

        return new PageResponseDto<>(page, totalCount, content);
    }

    //커뮤니티 전체조회(커뮤니티 게시글 + 커뮤니티 좋아요 + 페이징)
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<CommunityBoardAllResponseDto>> getAllCommunityBoard(int page,
        int size, String titleCond, String contentsCond, String nicknameCond, Long hashtagId, String chatStatus, String sort) {
        // 1. 검색 조건을 객체로 저장
        CommunitySearchCondition searchCondition = new CommunitySearchCondition(titleCond,
            contentsCond, nicknameCond, hashtagId, chatStatus, sort);

        // 2. 검색조건을 포함하여 전체조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.communityAllList(
            searchCondition, pageRequest);
        //3. 결과를 반환
        List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getContent();
        long totalCount = allCommunityBoardList.getTotalElements();

        return new PageResponseDto<>(page, totalCount, content);
    }

    @Override
    @Transactional
    public PageResponseDto<List<CommunityBoardAllResponseDto>> getMyCommunityBoard(int page, int size,
        User user) {
        // 2. 검색조건을 포함하여 전체조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.communityMyList(pageRequest, user.getId());

        //3. 결과를 반환
        List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getContent();
        long totalCount = allCommunityBoardList.getTotalElements();
        return new PageResponseDto<>(page, totalCount, content);
    }


    // (어드민) 커뮤니티 수정
    @Override
    @Transactional
    public void adminUpdateCommunityBoard(Long boardId, CommunityBoardRequestDto requestDto) {
        CommunityBoard community = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        // 커뮤니티에 있는 태그를 전부 지우고 새로 저장한다.
        communityTagRepository.deleteTagAllByBoardId(boardId);
        List<CommunityTag> communityTags = new ArrayList<>();
        for (Long tag : requestDto.getTagList()) {
            Hashtag hashtag = hashtagRepository.findById(tag)
                .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG));
            CommunityTag communityTag = new CommunityTag(community, hashtag);
            communityTagRepository.save(communityTag);
            communityTags.add(communityTag);
        }

        // 커뮤니티에 있는 이미지를 전부 지우고 새로 저장한다.
        communityBoardImgRepository.deleteImgAllByBoardId(boardId);
        List<CommunityBoardImg> communityImgList = new ArrayList<>();
        for (String imgUrl : requestDto.getImgList()) {
            CommunityBoardImg communityImg = new CommunityBoardImg(imgUrl, community);
            communityBoardImgRepository.save(communityImg);
            communityImgList.add(communityImg);
        }

        community.updateBoard(
            requestDto.getTitle(),
            requestDto.getContents(),
            communityTags,
            communityImgList,
            requestDto.getChatStatus(),
            requestDto.getChatMemCnt()
        );
    }

    // (어드민) 커뮤니티 삭제
    @Override
    @Transactional
    public void adminDeleteCommunityBoard(Long boardId) {
        CommunityBoard communityBoard = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        // 1. Comment Id에 해당하는 Like를 전부 지운다.
        List<Long> commentIds = commentRepository.findIdsByCommunityBoardId(boardId);
        likeCommentRepository.deleteLikeAllByInCommentIds(commentIds);

        // 2. Comment를 전부 지운다.
        commentRepository.deleteCommentAllByInBoardId(boardId);

        // 3. Tag, Img, CommunityLike를 전부 지운다.
        communityTagRepository.deleteTagAllByBoardId(communityBoard.getId());
        communityBoardImgRepository.deleteImgAllByBoardId(communityBoard.getId());
        likeBoardRepository.deleteLikeAllByInBoardId(boardId);

        // 4. 모든 연관관계를 지웠으니 이제 게시글을 지운다.
        boardRepository.deleteById(communityBoard.getId());
    }

    //내가 작성한 채팅방 리스트 전체조회
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<CommunityBoardAllResponseDto>> getMyChatBoardList(int page, int size, Long userId) {

        // 1. 검색조건을 포함하여 전체조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.myChatBoardList(userId, pageRequest);

        //2. 결과를 반환
        List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getContent();
        long totalCount = allCommunityBoardList.getTotalElements();

        return new PageResponseDto<>(page, totalCount, content);
    }

    //알림 기능을 위함 게시글 단건 조회
    @Override
    @Transactional(readOnly = true)
    public CommunityBoardOneResponseDto getBoard(Long boardId){

        CommunityBoard board  = boardRepository.findById(boardId).orElseThrow(()-> new CustomException(
            Status.NOT_FOUND_POST));

        CommunityBoardOneResponseDto commBoardResponse = CommunityBoardOneResponseDto.builder()
                                                                    .nickName(board.getUser().getNickName())
                                                                    .title(board.getTitle())
                                                                    .build();
        return commBoardResponse;
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