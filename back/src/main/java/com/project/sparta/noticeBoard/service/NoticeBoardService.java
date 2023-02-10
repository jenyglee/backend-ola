package com.project.sparta.noticeBoard.service;

import com.project.sparta.admin.entity.Admin;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.user.entity.User;

import java.util.List;
import java.util.Optional;


public interface NoticeBoardService {




     NoticeBoard createNoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto, Admin admin); // 작성

     void deleteNoticeBoard(Long id, Admin admin);   //  삭제

     NoticeBoardResponseDto updateNoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto, Long boardId, Admin admin);  //수정

     NoticeBoardResponseDto getNoticeBoard(Long boardId, User user);

     PageResponseDto<List<NoticeBoardResponseDto>>getAllNoticeBoard(int offset, int limit, User user);


}
