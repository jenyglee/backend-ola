package com.project.sparta.like.repository;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.like.entity.BoardLike;
import com.project.sparta.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;


public interface LikeCommentRepository extends JpaRepository<CommentLike,Long> {


    Optional<CommentLike> findByUserEmailAndComment(String email, CommunityComment comment);

    long countByComment(CommunityComment comment);

//    List<CommentLike> findByComment(CommunityComment comment);

}