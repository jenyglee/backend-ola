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
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final NoticeBoardRepository noticeBoardRepository;

    //공지글 작성
    @Override
    @Transactional
    public Long createNoticeBoard(NoticeBoardRequestDto requestDto, User user) {
        // TODO 익셉션 추가 : Title, Contents 중 ""인 경우

        // 0: SERVICE, 1: UPDATE, 2: EVENT
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
    @Transactional
    public void deleteNoticeBoard(Long id, User user) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_POST));
        noticeBoardRepository.delete(noticeBoard);
    }

    //공지글 수정
    @Override
    @Transactional
    public void updateNoticeBoard(Long id, NoticeBoardRequestDto requestDto, User user) {
        // TODO 익셉션 추가 : Title, Contents 중 ""인 경우

        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(() -> new CustomException(NOT_FOUND_POST));
        noticeBoard.update(requestDto.getTitle(), requestDto.getContents(), requestDto.getCategory());

        noticeBoardRepository.saveAndFlush(noticeBoard);
    }

    //공지글 단건 조회
    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public PageResponseDto<List<NoticeBoardResponseDto>> getAllNoticeBoard(int page, int size, String category) {
        long total;
        List<NoticeBoardResponseDto> content = new ArrayList<>();
        // 1. 카테고리가 있을 경우
        if (category != null) {
            NoticeCategoryEnum categoryEnum = NoticeCategoryEnum.SERVICE;
            if (category.equals("UPDATE")) {
                categoryEnum = NoticeCategoryEnum.UPDATE;
            } else if (category.equals("EVENT")) {
                categoryEnum = NoticeCategoryEnum.EVENT;
            }

            QueryResults<NoticeBoardResponseDto> noticeBoardList = noticeBoardRepository.findAllByCategory(
                    categoryEnum, page, size);

            content = noticeBoardList.getResults();
            total = noticeBoardList.getTotal();
        } else {
            // 2. 카테고리가 비었을 경우
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<NoticeBoard> boardList = noticeBoardRepository.findAll(pageRequest);
            total = boardList.getTotalElements();

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
        }

        return new PageResponseDto<>(page, total, content);
    }
}