package com.project.sparta.noticeBoard.service;

import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class NoticeBoardServiceImplTest {

    @Autowired
    NoticeBoardService noticeBoardService;
    @Autowired
    NoticeBoardRepository noticeBoardRepository;


    @Test
    void createNoticeBoard() {
        NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("rr", "dd");
        NoticeBoard save = noticeBoardService.createNoticeBoard(requestDto);

        Assertions.assertThat(save.getTitle()).isEqualTo("rr");




    }

    @Test
    void deleteNoticeBoard() {
    }

    @Test
    void updateNoticeBoard() {
    }

    @Test
    void getNoticeBoard() {
    }

    @Test
    void getAllNoticeBoard() {
    }
}