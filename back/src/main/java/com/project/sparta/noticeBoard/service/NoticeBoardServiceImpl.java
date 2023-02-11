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
    public NoticeBoardResponseDto createNoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto, String nickName) {

        NoticeBoard noticeBoard = new NoticeBoard(noticeBoardRequestDto.getTitle(), noticeBoardRequestDto.getContents(), nickName);

        noticeBoardRepository.saveAndFlush(noticeBoard);

        NoticeBoard resultNotice = noticeBoardRepository.findById(noticeBoard.getId()).orElseThrow(() -> new CustomException(INVALID_USER));

        return new NoticeBoardResponseDto(resultNotice.getId(), resultNotice.getNickName(), resultNotice.getTitle(),
                resultNotice.getContents(), resultNotice.getModifiedAt(), resultNotice.getCreateAt());
    }

    //공지글 삭제
    @Override
    public void deleteNoticeBoard(Long noticeId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeId).orElseThrow(() -> new CustomException(NOT_FOUND_POST));
        noticeBoardRepository.delete(noticeBoard);
    }

    //공지글 수정
    @Override
    public void updateNoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto, Long noticeId) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeId).orElseThrow(() -> new CustomException(NOT_FOUND_POST));
        noticeBoard.update(noticeBoardRequestDto.getTitle(), noticeBoardRequestDto.getContents());
    }

    //공지글 단건 조회
    @Override
    public NoticeBoardResponseDto getNoticeBoard(Long noticeId) {

        NoticeBoard noticeBoard = noticeBoardRepository.findById(noticeId).orElseThrow(() -> new CustomException(NOT_FOUND_POST));

        return new NoticeBoardResponseDto(noticeBoard.getId(), noticeBoard.getNickName(), noticeBoard.getTitle(),
                noticeBoard.getContents(), noticeBoard.getModifiedAt(), noticeBoard.getCreateAt());
    }

    //공지글 전체조회
    @Override
    public PageResponseDto<List<NoticeBoardResponseDto>> getAllNoticeBoard(int offset, int limit, User user) {

        // 1. 페이징으로 요청해서 조회
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<NoticeBoard> results = noticeBoardRepository.findAll(pageRequest);

        // 2. 데이터, 전체 개수 추출
        Page<NoticeBoardResponseDto> allList = results.map(noticeBoard -> new NoticeBoardResponseDto(noticeBoard.getId(), noticeBoard.getNickName(), noticeBoard.getTitle(),
                noticeBoard.getContents(), noticeBoard.getModifiedAt(), noticeBoard.getCreateAt()));

        List<NoticeBoardResponseDto> content = allList.getContent();
        long totalElements = allList.getTotalElements();

        //4. 클라이언트에 응답(현재페이지, 전체 건수, 데이터 포함)
        return new PageResponseDto<>(offset, totalElements, content);
    }
}
