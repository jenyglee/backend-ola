package com.project.sparta.like.repository;

import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.communityComment.dto.CommentResponseDto;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.communityComment.service.CommunityCommentService;
import com.project.sparta.exception.CustomException;
import com.project.sparta.like.service.CommentLikeService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeCommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LikeCommentRepository likeCommentRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CommunityBoardService communityBoardService;

    @Autowired
    CommunityCommentService communityCommentService;

    @Autowired
    CommentLikeService commentLikeService;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("댓글 존재 확인 에러")
    @Transactional
    void existenceCommentCheckError() {
        User createCommentUser = createUser();
        User createBoardAndCommentLikeUser = createUser();
        //게시글 생성
        CommunityBoard board = createBoard(createBoardAndCommentLikeUser);

        //댓글생성
        Long comment = createComment(board.getId(), createCommentUser);
        Long commentId = 1234L;

        //then
        assertThrows(CustomException.class, ()-> commentLikeService.likeComment(commentId, createCommentUser));

    }

    @Test
    @DisplayName("좋아요 중복 확인 에러")
    @Transactional
    void likeExistenceCheckError() {
        //given
        User createCommentUser = createUser();
        User createBoardAndCommentLikeUser = createUser();
        User user2 = createUser();
        //게시글 생성
        CommunityBoard board = createBoard(createBoardAndCommentLikeUser);
        //댓글생성
        Long comment = createComment(board.getId(), createCommentUser);
        //댓글 좋아요 누르기
        Long likeCommentId = commentLikeService.likeComment(comment, createBoardAndCommentLikeUser);

        assertThrows(CustomException.class, ()->  commentLikeService.likeComment(comment, createBoardAndCommentLikeUser));
    }
    @Test
    @DisplayName("내가 누른 댓글좋아요만 삭제")
    @Transactional
    void likeCommentClickUserCheckError() {
        //given
        User createCommentUser = createUser();
        User createBoardAndCommentLikeUser = createUser();
        User user2 = createUser();
        //게시글 생성
        CommunityBoard board = createBoard(createBoardAndCommentLikeUser);
        //댓글생성
        Long comment = createComment(board.getId(), createCommentUser);
        //댓글 좋아요 누르기
        Long likeCommentId = commentLikeService.likeComment(comment, createBoardAndCommentLikeUser);
        //then
        assertThrows(CustomException.class, ()->  commentLikeService.unLikeComment(likeCommentId,user2));
    }


    //유저생성 메서드
    public User createUser(){
        String randomUser = "user"+ UUID.randomUUID().toString().substring(0,5);
        User user1 = User
                .userBuilder()
                .email(randomUser +"@olaUser.com")
                .password("1234")
                .nickName("사용자"+randomUser)
                .age(99)
                .phoneNumber("010-1111-2222")
                .userImageUrl("sdf.jpg")
                .build();
        em.persist(user1);
        return userRepository.findById(user1.getId()).get();
    }

    //게시글 생성 메서드
    public CommunityBoard createBoard(User user){
        //게시글 생성
        List taglist = Arrays.asList(1L, 2L, 3L, 4L);
        List imgList = Arrays.asList("1,2,3,4");

        CommunityBoardRequestDto requestDto = CommunityBoardRequestDto.builder()
                .title("첫번째 게시글")
                .chatMemCnt(0)
                .contents("첫번쨰 컨텐츠")
                .tagList(taglist)
                .imgList(imgList)
                .chatStatus("Y")
                .build();
        return communityBoardService.createCommunityBoard(requestDto, user);

    }

    //댓글 생성 메서드
    public Long createComment(Long boardId, User user){
        CommunityRequestDto communityRequestDto = new CommunityRequestDto("댓글입니다.");

        CommentResponseDto communityComments = communityCommentService.createCommunityComments(boardId, communityRequestDto, user);

        return communityComments.getId();

    }
}