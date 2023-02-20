package com.project.sparta.noticeBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public void createNoticeBoard(NoticeBoardRequestDto requestDto, User user) {
        // 0: SERVICE, 1: UPDATE, 2: EVENT
        NoticeBoard noticeBoard = new NoticeBoard(user, requestDto.getTitle(), requestDto.getContents(), requestDto.getCategory());
        noticeBoardRepository.saveAndFlush(noticeBoard);
    }

    //공지글 삭제
    @Override
    public void deleteNoticeBoard(Long id, User user) {
        NoticeBoard noticeBoard = noticeBoardRepository.findByIdAndUser_NickName(id, user.getNickName()).orElseThrow(() -> new CustomException(NOT_FOUND_POST));
        noticeBoardRepository.delete(noticeBoard);
    }

    //공지글 수정
    @Override
    public void updateNoticeBoard(Long id, NoticeBoardRequestDto requestDto, User user) {
        NoticeBoard noticeBoard = noticeBoardRepository.findByIdAndUser_NickName(id, user.getNickName()).orElseThrow(() -> new CustomException(NOT_FOUND_POST));
        noticeBoard.update(requestDto.getTitle(), requestDto.getContents(), requestDto.getCategory());
    }

    //공지글 단건 조회
    @Override
    @Transactional(readOnly = true)
    public NoticeBoardResponseDto getNoticeBoard(Long id, User user) {

        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(() -> new CustomException(NOT_FOUND_POST));

        return new NoticeBoardResponseDto(noticeBoard.getId(), noticeBoard.getUser().getNickName(), noticeBoard.getTitle(),
                noticeBoard.getContents(), noticeBoard.getCategory(), noticeBoard.getModifiedAt(), noticeBoard.getCreateAt());
    }

    //공지글 전체조회
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<NoticeBoardResponseDto>> getAllNoticeBoard(int page, int size, User user) {
        // 1. 페이징으로 요청해서 조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<NoticeBoard> results = noticeBoardRepository.findAll(pageRequest);

        // 2. 데이터, 전체 개수 추출
        Page<NoticeBoardResponseDto> allList = results.map(noticeBoard -> new NoticeBoardResponseDto(noticeBoard.getId(), noticeBoard.getUser().getNickName(), noticeBoard.getTitle(),
                noticeBoard.getContents(), noticeBoard.getCategory(), noticeBoard.getModifiedAt(), noticeBoard.getCreateAt()));

        List<NoticeBoardResponseDto> content = allList.getContent();
        long totalElements = allList.getTotalElements();

        //4. 클라이언트에 응답(현재페이지, 전체 건수, 데이터 포함)
        return new PageResponseDto<>(page, totalElements, content);
    }
}
