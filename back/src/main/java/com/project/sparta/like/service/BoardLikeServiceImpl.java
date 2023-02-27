package com.project.sparta.like.service;

import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.like.entity.BoardLike;
import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardLikeServiceImpl implements BoardLikeService {

    private final BoardRepository boardRepository;
    private final LikeBoardRepository likeBoardRepository;
    @Override
    public void likeBoard(Long id, User user){
        //아이디값으로 보드 찾고
        CommunityBoard board = boardRepository.findById(id).orElseThrow(()->new CustomException(Status.NOT_FOUND_POST));

        //레파지토리에서 이메일로 좋아요 있는지 없는지 찾아서 없으면
        Optional<BoardLike> findByUserEmail = likeBoardRepository.findByUserEmailAndBoard(user.getEmail(),board);

        if(findByUserEmail.isPresent()) throw new CustomException(Status.CONFLICT_LIKE);
            //보드라이크 생성
            BoardLike boardLike = BoardLike.builder()
                .userNickName(user.getNickName())
                .userEmail(user.getEmail())
                .board(board)
                .build();

            //레파지토리에 저장
            likeBoardRepository.save(boardLike);





    }

    @Override
    public void unLikeBoard(Long id, User user){
        //아이디값으로 보드 찾고
        CommunityBoard board = boardRepository.findById(id).orElseThrow(()->new CustomException(Status.NOT_FOUND_POST));
        //라이크 레파지토리에서 이메일로 찾고이미 누른 좋아요라면
        BoardLike findByUserEmail = likeBoardRepository.findByUserEmailAndBoard(user.getEmail(),board).orElseThrow(()->new CustomException(Status.CONFLICT_LIKE));
        //레파지토리에서 좋아요를 삭제한다.
        likeBoardRepository.delete(findByUserEmail);

    }


}
