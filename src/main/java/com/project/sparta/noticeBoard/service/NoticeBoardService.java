package com.project.sparta.noticeBoard.service;

import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;


public interface NoticeBoardService {



     NoticeBoardResponseDto createNoticeBoard(Long id, NoticeBoardRequestDto requestDto); // 작성

     void deleteNoticeBoard(Long id, NoticeBoardRequestDto requestDto);   //  삭제
}
