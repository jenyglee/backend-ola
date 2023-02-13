package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import java.util.Optional;

import com.project.sparta.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<CommunityBoard, Long>, BoardRepositoryCustom {
  Page<CommunityBoard> findById(Pageable pageable,Long Id);
  Page<CommunityBoard> findAll(Pageable pageable);
  Page<CommunityBoard> findAllById(Pageable pageable, Long id);
  // Long countByUserId(Long userId);
}
