package com.project.sparta.noticeBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;

import java.util.List;
import java.util.Optional;


public interface NoticeBoardService {




     NoticeBoard createNoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto); // 작성

     void deleteNoticeBoard(Long id);   //  삭제

     NoticeBoardResponseDto updateNoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto,Long id);  //수정

     NoticeBoardResponseDto getNoticeBoard(Long id);

     PageResponseDto<List<NoticeBoardResponseDto>>getAllNoticeBoard(int offset, int limit);


}
