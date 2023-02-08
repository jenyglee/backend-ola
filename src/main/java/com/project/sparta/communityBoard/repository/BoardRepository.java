package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<CommunityBoard, Long> {
  Optional<CommunityBoard> findByNickName(String userName);
}
