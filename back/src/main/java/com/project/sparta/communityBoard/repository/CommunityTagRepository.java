package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.entity.CommunityTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommunityTagRepository extends JpaRepository<CommunityTag, Long> {
    List<CommunityTag> findAllByCommunityBoard_Id(Long boardId);

    @Query("delete from CommunityTag ct where ct.communityBoard.id=:boardId")
    @Modifying
    void deleteTagAllByBoardId(@Param("boardId") Long boardId);
}
