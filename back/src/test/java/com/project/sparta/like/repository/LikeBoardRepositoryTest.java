package com.project.sparta.like.repository;

import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.exception.CustomException;
import com.project.sparta.like.service.BoardLikeService;
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
class LikeBoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    LikeBoardRepository likeBoardRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CommunityBoardService communityBoardService;

    @Autowired
    BoardLikeService boardLikeService;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("게시글 존재 확인 에러")
    @Transactional
    void existenceCheckError() {
        //given
        //유저생성
        User user = createUser();
        Long boardId = 1234L;
        //then
        assertThrows(CustomException.class, ()-> boardLikeService.likeBoard(boardId, user));
    }

    @Test
    @DisplayName("좋아요 존재 확인 에러")
    @Transactional
    void likeExistenceCheckError() {
        //given
        //유저생성
        User user = createUser();
        User user2 = createUser();
        //게시글 생성
        CommunityBoard board = createBoard(user2);
        Long boardLikeId1 = boardLikeService.likeBoard(board.getId(), user);

        assertThrows(CustomException.class, ()-> boardLikeService.likeBoard(board.getId(), user));
    }

    @Test
    @DisplayName("다른 좋아요를 삭제했을 때 에러")
    @Transactional
    void likeClickUserCheckError() {
        //내가 누른 좋아요가 아닌데도 삭제가 되나..?
        //given
        //유저생성
        User user = createUser();
        User user2 = createUser();
        //게시글 생성
        CommunityBoard board = createBoard(user2);
        Long boardLikeId1 = boardLikeService.likeBoard(board.getId(), user);

        //then
        assertThrows(CustomException.class, ()->  boardLikeService.unLikeBoard(boardLikeId1,user2));
    }

    @Test
    @DisplayName("다른 좋아요 클릭했을때 에러")
    @Transactional
    void otherLikeCheckError() {
        //given
        //유저생성
        User user = createUser();
        //게시글 생성
        CommunityBoard board = createBoard(user);
//        Long boardLikeId1 = boardLikeService.likeBoard(board.getId(), user);
        Long boardLikeId1 = 2836L;

        //then
        assertThrows(CustomException.class, ()-> boardLikeService.unLikeBoard(boardLikeId1,user));
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
}