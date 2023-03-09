package com.project.sparta.communityComment.service;

import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.communityComment.dto.CommentResponseDto;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.repository.CommentRepository;
import com.project.sparta.like.repository.LikeCommentRepository;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@SpringBootTest
public class CommunityCommentServiceImplTest {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommunityCommentService communityCommentService;
    @Autowired
    CommunityBoardService communityBoardService;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;

    List taglist = Arrays.asList(1L, 2L, 3L, 4L);
    List imgList = Arrays.asList("1,2,3,4");

    @Test
    @Transactional
    void createCommunityComments() {
        //given
        String randomUser = "user"+ UUID.randomUUID();
        User user1 = User
                .userBuilder()
                .email(randomUser+"@naver.com")
                .password("1234")
                .nickName("99번째 사용자")
                .age(929)
                .phoneNumber("010-0011-2222")
                .userImageUrl("sdf.jpg")
                .build();



        userRepository.save(user1);
        CommunityBoardRequestDto communityBoardRequestDto = new CommunityBoardRequestDto("dd","gd","Y",14,taglist,imgList);
        CommunityBoard communityBoard = communityBoardService.createCommunityBoard(communityBoardRequestDto,user1);
        boardRepository.save(communityBoard);


        CommunityRequestDto communityRequestDto =CommunityRequestDto.builder()
                .contents("굿굿")
                .build();
        //when
        CommentResponseDto communityComment = communityCommentService.createCommunityComments(communityBoard.getId(),communityRequestDto,user1);
        //then
        assertThat(communityComment.getContents().equals("굿굿"));

    }

    @Test
    @Transactional
    void updateCommunityComments() {
        //given
        String randomUser = "user"+ UUID.randomUUID();
        User user1 = User
                .userBuilder()
                .email(randomUser+"@naver.com")
                .password("1234")
                .nickName("99번째 사용자")
                .age(929)
                .phoneNumber("010-0011-2222")
                .userImageUrl("sdf.jpg")
                .build();

        userRepository.save(user1);
        CommunityBoardRequestDto communityBoardRequestDto = new CommunityBoardRequestDto("dd","gd","Y",14,taglist,imgList);
        CommunityBoard communityBoard = communityBoardService.createCommunityBoard(communityBoardRequestDto,user1);
        boardRepository.save(communityBoard);

        CommunityRequestDto communityRequestDto =CommunityRequestDto.builder()
                .contents("굿굿")
                .build();
       CommentResponseDto commentResponseDto=communityCommentService.createCommunityComments(communityBoard.getId(),communityRequestDto,user1);
       //when
       CommunityRequestDto communityRequestDto1 = CommunityRequestDto.builder()
               .contents("안녕하셍")
               .build();

       communityCommentService.updateCommunityComments(commentResponseDto.getId(),communityRequestDto1,user1);



       //then
       assertThat(commentResponseDto.getContents().equals("안녕하셍"));
    }

    @Test
    @Transactional
    void deleteCommunityComments() {
        //given
        String randomUser = "user"+ UUID.randomUUID();
        User user1 = User
                .userBuilder()
                .email(randomUser+"@naver.com")
                .password("1234")
                .nickName("99번째 사용자")
                .age(929)
                .phoneNumber("010-0011-2222")
                .userImageUrl("sdf.jpg")
                .build();

        userRepository.save(user1);
        CommunityBoardRequestDto communityBoardRequestDto = new CommunityBoardRequestDto("dd","gd","Y",14,taglist,imgList);
        CommunityBoard communityBoard = communityBoardService.createCommunityBoard(communityBoardRequestDto,user1);
        boardRepository.save(communityBoard);

        CommunityRequestDto communityRequestDto =CommunityRequestDto.builder()
                .contents("굿굿")
                .build();
        CommentResponseDto communityComment = communityCommentService.createCommunityComments(communityBoard.getId(),communityRequestDto,user1);

        //when

        //commentDelete 하기 전 commentSize

        Long beforeCommentSize= commentRepository.count();

        communityCommentService.deleteCommunityComments(communityComment.getId(),user1);

        //boardDelete 하고 나서 commentSize

        Long afterCommentSize= commentRepository.count();
        //then
        assertThat(afterCommentSize).isLessThan(beforeCommentSize);

    }
}