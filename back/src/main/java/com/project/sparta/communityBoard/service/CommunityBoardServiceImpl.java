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

    long chatCount = 0;
    long boardCount = 0;
    private final UserRepository userRepository;

    //커뮤니티 생성
    @Override
    @Transactional
    public CommunityBoard createCommunityBoard(CommunityBoardRequestDto requestDto, User user) {
        // 1. 커뮤니티 보드를 생성하고 DB에 저장
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

        // 2. 저장된 커뮤니티 보드에 태그 리스트를 저장
        List<CommunityTag> communityTagList = getCommunityTags(requestDto, communityBoard);
        communityBoard.updateCommunityTag(communityTagList);

        // 3. 저장된 커뮤니티 보드에 이미지 리스트를 저장
        List<CommunityBoardImg> communityImgList = getCommunityBoardImgs(requestDto,
            communityBoard);
        communityBoard.updateCommunityImg(communityImgList);

        // 4. 자동 등업 체크
        autoGradeUp(chatCount, boardCount, user, communityBoard);

        // 5. 채팅방 생성을 위한 return
        return board;
    }

    // 커뮤니티 수정
    @Override
    @Transactional
    public void updateCommunityBoard(Long boardId, CommunityBoardRequestDto requestDto, User user) {
        CommunityBoard communityBoard = boardRepository.findByIdAndUser_NickName(boardId,
                user.getNickName())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        // 1. 커뮤니티에 있는 태그를 전부 지우고 새로 저장
        communityTagRepository.deleteTagAllByBoardId(boardId);
        List<CommunityTag> communityTags = getCommunityTags(requestDto, communityBoard);

        // 2. 커뮤니티에 있는 이미지를 전부 지우고 새로 저장
        communityBoardImgRepository.deleteImgAllByBoardId(boardId);
        List<CommunityBoardImg> communityBoardImgs = getCommunityBoardImgs(requestDto,
            communityBoard);

        // 3. 커뮤니티 수정
        communityBoard.updateBoard(
            requestDto.getTitle(),
            requestDto.getContents(),
            communityTags,
            communityBoardImgs,
            requestDto.getChatStatus(),
            requestDto.getChatMemCnt()
        );
    }

    // 커뮤니티 삭제
    @Override
    @Transactional
    public void deleteCommunityBoard(Long boardId, User user) {
        CommunityBoard communityBoard = boardRepository.findByIdAndUser_NickName(boardId,
                user.getNickName())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        // 1. Comment Id에 해당하는 Like를 전부 삭제
        List<Long> commentIds = commentRepository.findIdsByCommunityBoardId(boardId);
        likeCommentRepository.deleteLikeAllByInCommentIds(commentIds);

        // 2. Comment를 전부 삭제
        commentRepository.deleteCommentAllByInBoardId(boardId);

        // 3. Tag, Img, CommunityLike를 전부 삭제
        communityTagRepository.deleteTagAllByBoardId(communityBoard.getId());
        communityBoardImgRepository.deleteImgAllByBoardId(communityBoard.getId());
        likeBoardRepository.deleteLikeAllByInBoardId(boardId);

        // 4. 마지막으로 커뮤니티 삭제
        boardRepository.deleteById(communityBoard.getId());
    }


    //커뮤니티 단건 조회
    @Override
    @Transactional(readOnly = true)
    public CommunityBoardOneResponseDto getCommunityBoard(Long boardId, int commentPage,
        int commentSize, String nickname) {
        PageRequest pageRequest = PageRequest.of(commentPage, commentSize);
        CommunityBoardOneResponseDto communityBoard = boardRepository.getBoard(boardId, pageRequest,
            nickname);
        return communityBoard;
    }

    //커뮤니티 전체조회(커뮤니티 게시글 + 커뮤니티 좋아요 + 페이징)
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<CommunityBoardAllResponseDto>> getAllCommunityBoard(int page,
        int size, String titleCond, String contentsCond, String nicknameCond, Long hashtagId,
        String chatStatus, String sort) {
        // 1. 쿼리문에 적용을 위해 null값 정제화
        if (titleCond == null) {
            titleCond = "";
        }
        if (contentsCond == null) {
            contentsCond = "";
        }
        if (nicknameCond == null) {
            nicknameCond = "";
        }
        if (hashtagId == null) {
            hashtagId = 0L;
        }
        if (chatStatus == null) {
            chatStatus = "";
        }
        if (sort == null) {
            sort = "";
        }

        // 2. 검색 조건을 객체로 저장
        CommunitySearchCondition searchCondition = new CommunitySearchCondition(titleCond,
            contentsCond, nicknameCond, hashtagId, chatStatus, sort);

        // 3. 검색조건을 포함하여 전체조회
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
    public PageResponseDto<List<CommunityBoardAllResponseDto>> getMyCommunityBoard(int page,
        int size, User user) {
        // 1. user ID를 포함하여 전체조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.communityMyList(
            pageRequest, user.getId());

        //2. 결과를 반환
        List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getContent();
        long totalCount = allCommunityBoardList.getTotalElements();
        return new PageResponseDto<>(page, totalCount, content);
    }


    // (어드민) 커뮤니티 수정
    @Override
    @Transactional
    public void adminUpdateCommunityBoard(Long boardId, CommunityBoardRequestDto requestDto) {
        CommunityBoard communityBoard = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        // 1. 커뮤니티에 있는 태그를 전부 지우고 새로 저장
        communityTagRepository.deleteTagAllByBoardId(boardId);
        List<CommunityTag> communityTags = getCommunityTags(requestDto, communityBoard);

        // 2. 커뮤니티에 있는 이미지를 전부 지우고 새로 저장
        communityBoardImgRepository.deleteImgAllByBoardId(boardId);
        List<CommunityBoardImg> communityBoardImgs = getCommunityBoardImgs(requestDto,
            communityBoard);

        // 3. 커뮤니티 수정
        communityBoard.updateBoard(
            requestDto.getTitle(),
            requestDto.getContents(),
            communityTags,
            communityBoardImgs,
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
        // 1. Comment Id에 해당하는 Like를 전부 삭제
        List<Long> commentIds = commentRepository.findIdsByCommunityBoardId(boardId);
        likeCommentRepository.deleteLikeAllByInCommentIds(commentIds);

        // 2. Comment를 전부 삭제
        commentRepository.deleteCommentAllByInBoardId(boardId);

        // 3. Tag, Img, CommunityLike를 전부 삭제
        communityTagRepository.deleteTagAllByBoardId(communityBoard.getId());
        communityBoardImgRepository.deleteImgAllByBoardId(communityBoard.getId());
        likeBoardRepository.deleteLikeAllByInBoardId(boardId);

        // 4. 모든 연관관계를 지웠으니 이제 게시글을 삭제
        boardRepository.deleteById(communityBoard.getId());
    }

    // 내가 작성한 크루원 모집 커뮤니티 전체조회
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<CommunityBoardAllResponseDto>> getMyChatBoardList(int page,
        int size, Long userId) {
        // 1. 검색조건을 포함하여 전체조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.myChatBoardList(
            userId, pageRequest);

        //2. 결과를 반환
        List<CommunityBoardAllResponseDto> content = allCommunityBoardList.getContent();
        long totalCount = allCommunityBoardList.getTotalElements();

        return new PageResponseDto<>(page, totalCount, content);
    }

    //알림 기능을 위한 게시글 단건 조회
    @Override
    @Transactional(readOnly = true)
    public CommunityBoardOneResponseDto getBoard(Long boardId) {
        CommunityBoard board = boardRepository.findById(boardId)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));

        CommunityBoardOneResponseDto responseDto = CommunityBoardOneResponseDto.builder()
            .nickName(board.getUser().getNickName())
            .title(board.getTitle())
            .build();
        return responseDto;
    }

    // 자동 등업 체크
    @Override
    public void autoGradeUp(Long chatCount, Long boardCount, User user,
        CommunityBoard communityBoard) {
        // 1. 커뮤니티 작성 수가 2 이하일 때 -> 이 조건일 때만 쿼리가 날아갈 수 있도록 최적화
        if (boardCount < 2) {
            boardCount = boardRepositoryImpl.getBoardCount(user.getId());
        }

        // 2. 커뮤니티 작성 수가 2일 때 -> 등산매니아로 등업
        if (boardCount == 2) {
            communityBoard.updateMania(1);
            user.changeGrade(UserGradeEnum.MOUNTAIN_MANIA);
            userRepository.saveAndFlush(user);
        }

        // 3. 게시글 작성 수가 2 이상이고, 채팅방 개설 수가 2이하일 때 -> 이 조건일 때만 쿼리가 날아갈 수 있도록 최적화
        if (boardCount >= 2 && chatCount <= 2) {
            PageRequest pageRequest = PageRequest.of(0, 5);
            Page<CommunityBoardAllResponseDto> allCommunityBoardList = boardRepository.myChatBoardList(
                user.getId(), pageRequest);
            chatCount = allCommunityBoardList.getTotalElements();
        }

        // 4. 채팅방 개설 수가 2일 때 -> 산신령으로 등업
        if (chatCount == 2) {
            communityBoard.updateGod(1);
            user.changeGrade(UserGradeEnum.MOUNTAIN_GOD);
            userRepository.saveAndFlush(user);
        }
    }

    private List<CommunityBoardImg> getCommunityBoardImgs(CommunityBoardRequestDto requestDto,
        CommunityBoard communityBoard) {
        List<CommunityBoardImg> communityImgList = new ArrayList<>();

        requestDto.getImgList().stream()
            // 각각 이미지 url을 DB에 저장
            .forEach(imgUrl -> {
                CommunityBoardImg communityImg = new CommunityBoardImg(imgUrl, communityBoard);
                communityBoardImgRepository.save(communityImg);
                communityImgList.add(communityImg);
            });
        return communityImgList;
    }

    private List<CommunityTag> getCommunityTags(CommunityBoardRequestDto requestDto,
        CommunityBoard communityBoard) {
        List<CommunityTag> communityTagList = new ArrayList<>();

        requestDto.getTagList().stream()
            // 1. Hashtag ID로 태그를 조회
            .map(tagNum -> hashtagRepository.findById(tagNum)
                .orElseThrow(() -> new CustomException(NOT_FOUND_HASHTAG)))
            // 2. 각각 CommunityTag 객체로 만들어 DB에 저장
            .forEach(hashtag -> {
                CommunityTag tag = new CommunityTag(communityBoard, hashtag);
                communityTagRepository.save(tag);
                communityTagList.add(tag);
            });
        return communityTagList;
    }
}
