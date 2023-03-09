package com.project.sparta.admin.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.sparta.exception.CustomException;
import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
import com.project.sparta.noticeBoard.service.NoticeBoardService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminNoticeBoardRepositoryTest {
  @Autowired
  NoticeBoardService noticeBoardService;
  @Autowired
  NoticeBoardRepository noticeBoardRepository;
  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("게시글을 찾을 수 없는 경우")
  public void deleteNoticeBoard() {
    User admin = new User("duu", "1234", "관리자");
    assertThrows(CustomException.class, () -> noticeBoardService.deleteNoticeBoard(3L, admin));
  }
}
