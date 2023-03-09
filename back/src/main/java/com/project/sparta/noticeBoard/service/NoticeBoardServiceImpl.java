package com.project.sparta.noticeBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.noticeBoard.entity.NoticeCategoryEnum;
import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.entity.UserRoleEnum.Authority;
import com.querydsl.core.QueryResults;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.project.sparta.exception.api.Status.INVALID_USER;
import static com.project.sparta.exception.api.Status.NOT_FOUND_POST;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    //공지글 작성
    @Override
    public Long createNoticeBoard(NoticeBoardRequestDto requestDto, User user) {
        // 0: SERVICE, 1: UPDATE, 2: EVENT
        //TODO 인가는 JWT 필터와 컨트롤러에서 끝나기 때문에 넣을 필요가 없다.(수정완료)

        NoticeBoard noticeBoard = NoticeBoard.builder()
                .category(requestDto.getCategory())
                .contents(requestDto.getContents())
                .title(requestDto.getTitle())
                .user(user)
                .build();
        NoticeBoard board = noticeBoardRepository.saveAndFlush(noticeBoard);

        return board.getId();
    }

    //공지글 삭제
    @Override
    public void deleteNoticeBoard(Long id, User user) {
        //TODO 인가는 JWT 필터와 컨트롤러에서 끝나기 때문에 넣을 필요가 없다.(수정완료)

        NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_POST));
        noticeBoardRepository.delete(noticeBoard);

//        if(user.getRole()== UserRoleEnum.ADMIN) {
//            NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
//                .orElseThrow(() -> new CustomException(NOT_FOUND_POST));
//            noticeBoardRepository.delete(noticeBoard);
//        }
    }

    //공지글 수정
    @Override
    public void updateNoticeBoard(Long id, NoticeBoardRequestDto requestDto, User user) {
        //TODO 인가는 JWT 필터와 컨트롤러에서 끝나기 때문에 넣을 필요가 없다.(수정완료)

        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(() -> new CustomException(NOT_FOUND_POST));
        noticeBoard.update(requestDto.getTitle(), requestDto.getContents(), requestDto.getCategory());
//        if(user.getRole()== UserRoleEnum.ADMIN)
//        {
//            NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(() -> new CustomException(NOT_FOUND_POST));
//            noticeBoard.update(requestDto.getTitle(), requestDto.getContents(), requestDto.getCategory());
//        }
    }

    //공지글 단건 조회
    @Override
    public NoticeBoardResponseDto getNoticeBoard(Long id) {

        NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_POST));

        NoticeBoardResponseDto noticeBoardResponseDto = NoticeBoardResponseDto.builder()
                .id(noticeBoard.getId())
                .title(noticeBoard.getTitle())
                .contents(noticeBoard.getContents())
                .createdAt(noticeBoard.getCreateAt())
                .username(noticeBoard.getUser().getNickName())
                .category(noticeBoard.getCategory())
                .modifiedAt(noticeBoard.getModifiedAt())
                .build();

        return noticeBoardResponseDto;
    }

    //공지글 전체조회
    @Override
    public PageResponseDto<List<NoticeBoardResponseDto>> getAllNoticeBoard(int page, int size, String category) {
        long total;
        List<NoticeBoardResponseDto> content = new ArrayList<>();
        if (!category.isEmpty()) {
            // 카테고리로 조회
            System.out.println("카테고리로 조회!");
            // category를 Enum으로 바꾸기
            NoticeCategoryEnum categoryEnum = NoticeCategoryEnum.SERVICE;
            if (category.equals("UPDATE")) {
                categoryEnum = NoticeCategoryEnum.UPDATE;
            } else if (category.equals("EVENT")) {
                categoryEnum = NoticeCategoryEnum.EVENT;
            }

            // 1. 페이징으로 요청해서 조회
            //        PageRequest pageRequest = PageRequest.of(page, size);
            QueryResults<NoticeBoardResponseDto> noticeBoardList = noticeBoardRepository.findAllByCategory(
                    categoryEnum, page, size);

            // 2. 데이터, 전체 개수 추출
//            Page<NoticeBoardResponseDto> allList = results.map(noticeBoard -> new NoticeBoardResponseDto(noticeBoard.getId(), noticeBoard.getUser().getNickName(), noticeBoard.getTitle(),
//                    noticeBoard.getContents(), noticeBoard.getCategory(), noticeBoard.getModifiedAt(), noticeBoard.getCreateAt()));
            content = noticeBoardList.getResults();
            total = noticeBoardList.getTotal();
        } else {
            // 전체 조회
            System.out.println("전체조회!");
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<NoticeBoard> boardList = noticeBoardRepository.findAll(pageRequest);
            total = boardList.getTotalElements();
            //    Page<CommunityBoard> boards = boardRepository.findById(pageable,user.getId());
            //    List<CommunityBoardOneResponseDto> CommunityBoardOneResponseDtoList = boards.getContent()
            //        .stream()
            //        .map(CommunityBoardOneResponseDto::new)
            //        .collect(Collectors.toList());
            content = boardList.getContent()
                    .stream()
                    .map((obj) -> new NoticeBoardResponseDto(
                            obj.getId(),
                            obj.getUser().getNickName(),
                            obj.getTitle(),
                            obj.getContents(),
                            obj.getCategory(),
                            obj.getCreateAt(),
                            obj.getModifiedAt()
                    ))
                    .collect(Collectors.toList());
//            Long id, String username, String title, String contents, NoticeCategoryEnum category, LocalDateTime
//            createdAt, LocalDateTime modifiedAt
        }
//
//        List<NoticeBoardResponseDto> content = allList.getContent();
//        long totalElements = allList.getTotalElements();
//
//        //4. 클라이언트에 응답(현재페이지, 전체 건수, 데이터 포함)
        return new PageResponseDto<>(page, total, content);
    }
}