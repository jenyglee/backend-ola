package com.project.sparta.communityBoard.repository;

import com.project.sparta.communityBoard.entity.CommunityTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityTagRepository extends JpaRepository<CommunityTag, Long> {
    List<CommunityTag> findAllByCommunityBoard_Id(Long boardId);
    void deleteAllByCommunityBoard_Id(Long boardId);
}
