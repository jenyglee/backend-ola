package com.project.sparta.noticeBoard.service;

import com.amazonaws.services.dynamodbv2.xspec.N;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.noticeBoard.entity.NoticeCategoryEnum;
import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.sparta.exception.api.Status.NOT_FOUND_POST;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

//@SpringBootTest
//@Transactional
//class NoticeBoardServiceImplTest {

//    @Autowired
//    NoticeBoardRepository noticeBoardRepository;
//    @Autowired
//    NoticeBoardService noticeBoardService;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    EntityManager em;
//
//
//
//
//
//    @Test
//    void createNoticeBoard() {
//        //given
//        NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("제목","내용", NoticeCategoryEnum.SERVICE);
//        User admin = new User("user1@naver.com","1234", "관리자");
//        em.persist(admin);
//        //when
//        noticeBoardService.createNoticeBoard(requestDto,admin);
//        NoticeBoard noticeBoard = noticeBoardRepository.findByTitle("제목");
//        noticeBoardRepository.saveAndFlush(noticeBoard);
//
//        //then
//        assertThat(noticeBoard.getTitle()).isEqualTo("제목");
//    }
//
//
//
//
//     @Test
//     void updateNoticeBoard() {
//         //given
//         NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용",NoticeCategoryEnum.SERVICE);
//         User admin = new User("user1@naver.com","1234", "관리자");
//         em.persist(admin);
//         //when
//         noticeBoardService.createNoticeBoard(requestDto, admin);
//         NoticeBoardRequestDto noticeBoardRequestDto = new NoticeBoardRequestDto("타이틀2", "내용2",NoticeCategoryEnum.SERVICE);
//         noticeBoardService.updateNoticeBoard(admin.getId(),noticeBoardRequestDto,admin);
//        //then
//         List<NoticeBoard> all = noticeBoardRepository.findAll();
//         assertThat(all.get(0).getTitle()).isEqualTo("타이틀2");
//     }
//
//     @Test
//     void getNoticeBoard() {
//         //given
//         NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용",NoticeCategoryEnum.SERVICE);
//         User admin = new User("user1@naver.com","1234", "관리자");
//         em.persist(admin);
//         //when
//         noticeBoardService.createNoticeBoard(requestDto, admin);
//
//         NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard();
//         //then
//
//         assertThat(noticeBoard.getTitle()).isEqualTo("타이틀");
//     }
//
//     @Test
//     void getAllNoticeBoard() {
//         User admin = new User("user1@naver.com","1234", "관리자");
//         NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용",NoticeCategoryEnum.SERVICE);
//         noticeBoardService.createNoticeBoard(requestDto, admin);
//
//         NoticeBoardRequestDto requestDto2 = new NoticeBoardRequestDto("타이틀2", "내용2",NoticeCategoryEnum.SERVICE);
//         noticeBoardService.createNoticeBoard(requestDto2, admin);
//
//         PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(0, 2, admin);
//
//         List<NoticeBoardResponseDto> noticeList = allNoticeBoard.getData();
//
//         assertThat(noticeList.size()).isEqualTo(2);
//     }
//}