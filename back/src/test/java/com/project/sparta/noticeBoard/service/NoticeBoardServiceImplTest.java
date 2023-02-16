// package com.project.sparta.noticeBoard.service;
//
// import com.project.sparta.common.dto.PageResponseDto;
// import com.project.sparta.exception.CustomException;
// import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
// import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
// import com.project.sparta.noticeBoard.entity.NoticeBoard;
// import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
// import com.project.sparta.user.entity.User;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.transaction.annotation.Transactional;
//
// import java.util.List;
//
// import static com.project.sparta.exception.api.Status.NOT_FOUND_POST;
// import static org.assertj.core.api.Assertions.*;
//
// @SpringBootTest
// @Transactional
// class NoticeBoardServiceImplTest {
//
//     // @Autowired
//     // NoticeBoardService noticeBoardService;
//     // @Autowired
//     // NoticeBoardRepository noticeBoardRepository;
//     //
//
//     // @Test
//     // void createNoticeBoard() {
//     //     NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("제목이지롱~", "내용");
//     //
//     //     User admin = new User("user1@naver.com","1234", "관리자", "sdf.jpg");
//     //     NoticeBoardResponseDto notice = noticeBoardService.createNoticeBoard(requestDto, admin.getNickName());
//     //
//     //     assertThat(notice.getTitle()).isEqualTo("제목이지롱~");
//     // }
//     //
//     // //삭제 테스트는 의미가 딱히 없다고 생각합니닷 ㅠ
//     // @Test
//     // void deleteNoticeBoard() {
//     //     NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용");
//     //     User admin = new User("user1@naver.com","1234", "관리자", "sdf.jpg");
//     //     NoticeBoardResponseDto notice = noticeBoardService.createNoticeBoard(requestDto, admin.getNickName());
//     //
//     //     noticeBoardService.deleteNoticeBoard(notice.getId());
//     //
//     //     NoticeBoard result = noticeBoardRepository.findById(notice.getId()).orElseThrow(()-> new CustomException(NOT_FOUND_POST));
//     //     assertThat(result).isEqualTo(null);
//     // }
//     //
//     // @Test
//     // void updateNoticeBoard() {
//     //     NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용");
//     //     User admin = new User("user1@naver.com","1234", "관리자", "sdf.jpg");
//     //     NoticeBoardResponseDto notice = noticeBoardService.createNoticeBoard(requestDto, admin.getNickName());
//     //
//     //     NoticeBoardRequestDto noticeBoardRequestDto = new NoticeBoardRequestDto("타이틀2", "내용");
//     //     noticeBoardService.updateNoticeBoard(noticeBoardRequestDto, notice.getId());
//     //
//     //     List<NoticeBoard> all = noticeBoardRepository.findAll();
//     //     assertThat(all.get(0).getTitle()).isEqualTo("타이틀2");
//     // }
//     //
//     // @Test
//     // void getNoticeBoard() {
//     //     NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용");
//     //     User admin = new User("user1@naver.com","1234", "관리자", "sdf.jpg");
//     //     NoticeBoardResponseDto notice = noticeBoardService.createNoticeBoard(requestDto, admin.getNickName());
//     //
//     //     NoticeBoardResponseDto noticeBoard = noticeBoardService.getNoticeBoard(notice.getId());
//     //
//     //     assertThat(noticeBoard.getTitle()).isEqualTo("타이틀");
//     // }
//     //
//     // @Test
//     // void getAllNoticeBoard() {
//     //     User admin = new User("user1@naver.com","1234", "관리자", "sdf.jpg");
//     //     NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용");
//     //     noticeBoardService.createNoticeBoard(requestDto, admin.getNickName());
//     //
//     //     NoticeBoardRequestDto requestDto2 = new NoticeBoardRequestDto("타이틀2", "내용2");
//     //     noticeBoardService.createNoticeBoard(requestDto2, admin.getNickName());
//     //
//     //     PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(0, 2, admin);
//     //
//     //     List<NoticeBoardResponseDto> noticeList = allNoticeBoard.getData();
//     //
//     //     assertThat(noticeList.size()).isEqualTo(2);
//     // }
// }