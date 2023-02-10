package com.project.sparta.noticeBoard.service;

import com.project.sparta.admin.entity.Admin;
import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;
import static org.assertj.core.api.Assertions.*;
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
        NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용");
        Admin admin = new Admin("email", "1234", "관리자", UserRoleEnum.ADMIN, StatusEnum.ADMIN_REGISTERED, "07B4925039BE4219C76865C5CCB87466");
        NoticeBoard save = noticeBoardService.createNoticeBoard(requestDto, admin);

        assertThat(save.getTitle()).isEqualTo("타이틀");
    }

    @Test
    void deleteNoticeBoard() {
        NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용");
        Admin admin = new Admin("email", "1234", "관리자", UserRoleEnum.ADMIN, StatusEnum.ADMIN_REGISTERED, "07B4925039BE4219C76865C5CCB87466");
        NoticeBoard save = noticeBoardService.createNoticeBoard(requestDto, admin);
        noticeBoardService.deleteNoticeBoard(save.getId(), admin);

        List<NoticeBoard> all = noticeBoardRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    void updateNoticeBoard() {
        NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용");
        Admin admin = new Admin("email", "1234", "관리자", UserRoleEnum.ADMIN, StatusEnum.ADMIN_REGISTERED, "07B4925039BE4219C76865C5CCB87466");
        NoticeBoard save = noticeBoardService.createNoticeBoard(requestDto, admin);

        NoticeBoardRequestDto noticeBoardRequestDto = new NoticeBoardRequestDto("타이틀2", "내용");
        noticeBoardService.updateNoticeBoard(noticeBoardRequestDto, save.getId(), admin);

        List<NoticeBoard> all = noticeBoardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("타이틀2");
    }

    @Test
    void getNoticeBoard() {
        NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용");
        Admin admin = new Admin("email", "1234", "관리자", UserRoleEnum.ADMIN, StatusEnum.ADMIN_REGISTERED, "07B4925039BE4219C76865C5CCB87466");
        NoticeBoard save = noticeBoardService.createNoticeBoard(requestDto, admin);

        User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
        NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard(save.getId(), user1);

        assertThat(noticeBoard.getTitle()).isEqualTo("타이틀");
    }

    @Test
    void getAllNoticeBoard() {
        User user1 = new User("user1@naver.com","1234", "이재원", 10,"010-1234-1234","sdf.jpg");
        PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(0, 10, user1);

        List<NoticeBoardResponseDto> data = allNoticeBoard.getData();

        assertThat(data.size()).isEqualTo(0);
    }
}