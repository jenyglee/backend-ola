package com.project.sparta.noticeBoard.service;

import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;


    @Override
    public NoticeBoardResponseDto createNoticeBoard(Long id, NoticeBoardRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteNoticeBoard(Long id, NoticeBoardRequestDto requestDto) {

    }
}
