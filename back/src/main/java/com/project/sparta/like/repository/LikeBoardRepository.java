package com.project.sparta.like.repository;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.like.entity.BoardLike;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LikeBoardRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByUserEmailAndBoard(String email, CommunityBoard board);

    @Query("delete from BoardLike bl where bl.board.id = :boardId")
    @Modifying
    void deleteLikeAllByInBoardId(@Param("boardId") Long boardId);

    Long countByBoard_Id(Long id);
    Long countByBoard(CommunityBoard board);
}
