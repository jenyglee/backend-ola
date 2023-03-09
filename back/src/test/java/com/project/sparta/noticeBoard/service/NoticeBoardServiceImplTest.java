package com.project.sparta.noticeBoard.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.noticeBoard.entity.NoticeCategoryEnum;
import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;
import static com.project.sparta.exception.api.Status.NOT_FOUND_POST;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeBoardServiceImplTest {

    @Autowired
    NoticeBoardRepository noticeBoardRepository;
    @Autowired
    NoticeBoardService noticeBoardService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;


    @Test
    @Transactional
    void createNoticeBoard() {
        //given
        NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("우하하하ㅏ", "내용",
            NoticeCategoryEnum.SERVICE);

        String randomUser = "user"+ UUID.randomUUID();
        User admin = new User(randomUser, "1234", "관리자");

        //when
        Long boardId = noticeBoardService.createNoticeBoard(requestDto, userRepository.save(admin));
        NoticeBoard noticeBoard = noticeBoardRepository.findById(boardId).orElseThrow(()-> new CustomException(NOT_FOUND_POST));

        //then
        assertThat(noticeBoard.getId()).isEqualTo(boardId);
        assertThat(noticeBoard.getCategory()).isEqualTo(NoticeCategoryEnum.SERVICE);
    }


     @Test
     @Transactional
     void updateNoticeBoard() {
         //given
         NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("타이틀", "내용",NoticeCategoryEnum.SERVICE);
         String randomUser = "user"+ UUID.randomUUID();
         User admin = new User(randomUser, "1234", "관리자");

         //when
         Long bordId = noticeBoardService.createNoticeBoard(requestDto, userRepository.save(admin));
         NoticeBoardRequestDto noticeBoardRequestDto = new NoticeBoardRequestDto("타이틀2", "내용2",NoticeCategoryEnum.SERVICE);
         noticeBoardService.updateNoticeBoard(bordId,noticeBoardRequestDto,admin);
         NoticeBoard noticeBoard =noticeBoardRepository.findById(bordId).orElseThrow(
                 ()->new CustomException(NOT_FOUND_POST)
         );

        //then
         assertThat(noticeBoard.getTitle()).isEqualTo("타이틀2");
     }

     @Test
     void getNoticeBoard() {
         //given
         NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("우하하하", "내용",
                 NoticeCategoryEnum.SERVICE);
         String randomUser = "user"+ UUID.randomUUID();
         User admin = new User(randomUser, "1234", "관리자");

         //when
         Long boardId = noticeBoardService.createNoticeBoard(requestDto, userRepository.save(admin));
         NoticeBoard noticeBoard = noticeBoardRepository.findById(boardId).orElseThrow(
                 ()-> new CustomException(NOT_FOUND_POST));
         //then

         assertThat(noticeBoard.getTitle()).isEqualTo("우하하하");
     }

     @Test
     @Transactional
     void getAllNoticeBoard() {
        //given

         NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("우하하하1", "내용",
                 NoticeCategoryEnum.SERVICE);
         String randomUser = "user"+ UUID.randomUUID();
         User admin = new User(randomUser, "1234", "관리자");

         //when
         noticeBoardService.createNoticeBoard(requestDto, userRepository.save(admin));

         PageResponseDto<List<NoticeBoardResponseDto>> allNoticeBoard = noticeBoardService.getAllNoticeBoard(0,10, String.valueOf(NoticeCategoryEnum.SERVICE));

         List<NoticeBoardResponseDto> noticeList = allNoticeBoard.getData();
        //then
         assertThat(noticeList.size()).isEqualTo(10);
     }
     @Test
     @Transactional
     void deleteNoticeBoard(){
        //given
         NoticeBoardRequestDto requestDto = new NoticeBoardRequestDto("우하하하1", "내용",
                 NoticeCategoryEnum.SERVICE);
         String randomUser = "user"+ UUID.randomUUID();
         User admin = new User(randomUser, "1234", "관리자");

         //when
         Long boardId = noticeBoardService.createNoticeBoard(requestDto, userRepository.save(admin));

         //boardDelete 하기 전 boardSize
         Long beforeBoardSize= noticeBoardRepository.count();

         noticeBoardService.deleteNoticeBoard(boardId,admin);

         //boardDelete 하고 나서 boardSize
         Long afterBoardSize= noticeBoardRepository.count();

         //then
         assertThat(afterBoardSize).isLessThan(beforeBoardSize);
     }

}


