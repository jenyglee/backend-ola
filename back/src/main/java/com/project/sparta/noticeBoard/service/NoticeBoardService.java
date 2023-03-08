package com.project.sparta.noticeBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.user.entity.User;

import java.util.List;


public interface NoticeBoardService {


     Long createNoticeBoard(NoticeBoardRequestDto requestDto, User user);   //공지글 작성

     void deleteNoticeBoard(Long id, User user);   //공지글 삭제

     void updateNoticeBoard(Long id, NoticeBoardRequestDto requestDto, User user);  //공지글 수정

     NoticeBoardResponseDto getNoticeBoard(Long id);       //공지글 단건조회

     PageResponseDto<List<NoticeBoardResponseDto>> getAllNoticeBoard(int page, int size, String category);        //공지글 전체조회


}
