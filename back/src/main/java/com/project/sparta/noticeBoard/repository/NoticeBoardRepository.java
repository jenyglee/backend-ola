package com.project.sparta.noticeBoard.repository;

import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface NoticeBoardRepository extends JpaRepository<NoticeBoard,Long>, NoticeBoardRepositoryCustom{
     Optional<NoticeBoard> findById(Long id);
     Optional<NoticeBoard> findByIdAndUser_NickName(Long id, String nickName);
}
