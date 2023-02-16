package com.project.sparta.like.repository;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.like.entity.BoardLike;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LikeBoardRepository extends JpaRepository<BoardLike, Long> {


    Optional<BoardLike> findByUserEmailAndBoard(String email, CommunityBoard board);

    Long countByBoard_Id(Long id);
}
