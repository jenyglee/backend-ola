package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.entity.CommunityBoardImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityBoardImgRepository extends JpaRepository<CommunityBoardImg, Long> {
    @Query("delete from CommunityBoardImg ci where ci.communityBoard.id=:boardId")
    @Modifying
    void deleteImgAllByBoardId(@Param("boardId") Long boardId);
}
