package com.project.sparta.like.service;


import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.like.entity.BoardLike;
import com.project.sparta.like.entity.CommentLike;
import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.like.repository.LikeCommentRepository;
import com.project.sparta.user.entity.User;
import java.security.PrivateKey;
import lombok.RequiredArgsConstructor;
import net.sf.ehcache.search.aggregator.Count;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeServiceImpl implements CommentLikeService{
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Override
    public void likeComment(Long id, User user){
        CommunityComment comment = commentRepository.findById(id).orElseThrow(()->new CustomException(Status.NOT_FOUND_POST));

        //레파지토리에서 이메일로 좋아요 있는지 없는지 찾아서 없으면
        Optional<CommentLike> findByUserEmail = likeCommentRepository.findByUserEmailAndComment(user.getEmail(),comment);

        if(findByUserEmail.isPresent()) throw new CustomException(Status.CONFLICT_LIKE);

        //보드라이크 생성
        CommentLike commentLike = CommentLike.builder()
                .userNickName(user.getNickName())
                .userEmail(user.getEmail())
                .comment(comment).build();

        //레파지토리에 저장

        likeCommentRepository.save(commentLike);

    }
    @Override
    public void unLikeComment(Long id, User user){
        //아이디값으로 보드 찾고
        CommunityComment comment = commentRepository.findById(id).orElseThrow(()->new CustomException(Status.NOT_FOUND_POST));
        //라이크 레파지토리에서 이메일로 찾고이미 누른 좋아요라면
        CommentLike findByUserEmail = likeCommentRepository.findByUserEmailAndComment(user.getEmail(),comment).orElseThrow(()->new CustomException(Status.CONFLICT_LIKE));
        //레파지토리에서 좋아요를 삭제한다.
        likeCommentRepository.delete(findByUserEmail);
    }

}
