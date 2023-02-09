package com.project.sparta.noticeBoard.repository;

import com.project.sparta.noticeBoard.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface NoticeBoardRepository extends JpaRepository<NoticeBoard,Long> {

     Optional<NoticeBoard>findByUsername(String username);
     Optional<NoticeBoard>findById(Long id);

    List<NoticeBoard> findAllById(Long id);
}
