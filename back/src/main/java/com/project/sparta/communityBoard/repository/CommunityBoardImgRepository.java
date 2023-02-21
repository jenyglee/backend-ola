package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.entity.CommunityBoardImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityBoardImgRepository extends JpaRepository<CommunityBoardImg, Long> {
    void deleteAllByCommunityBoard_Id(Long boardId);
}
