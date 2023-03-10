package com.project.sparta.communityComment.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.service.CommunityCommentService;
import com.project.sparta.exception.CustomException;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest

public class CommentRepositoryTest {

    @Autowired
    CommunityCommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("게시글이 존재하지 않을 경우")
    @Transactional
    public void createCommunityComment() {
        String randomUser1 = "user" + UUID.randomUUID();

        User user = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();
        User u = userRepository.save(user);

        CommunityRequestDto communityRequestDto =CommunityRequestDto.builder()
            .contents("굿굿")
            .build();

        assertThrows(CustomException.class, ()-> commentService.createCommunityComments(12345677775L, communityRequestDto, u));
    }


    @Test
    @DisplayName("댓글이 존재하지 않을 경우")
    @Transactional
    public void notFoundCommunityComment() {
        String randomUser1 = "user" + UUID.randomUUID();

        User user = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();
        User u = userRepository.save(user);

        CommunityRequestDto communityRequestDto =CommunityRequestDto.builder()
            .contents("굿굿")
            .build();

        assertThrows(CustomException.class, ()-> commentService.updateCommunityComments(12345677775L, communityRequestDto, u));
        assertThrows(CustomException.class, ()-> commentService.deleteCommunityComments(12345677775L, u));
    }

    @Test
    @DisplayName("댓글 내용이 비어있을 경우")
    @Transactional
    public void contentsIsEmpty() {
        String randomUser1 = "user" + UUID.randomUUID();

        User user = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();
        User u = userRepository.save(user);

        CommunityRequestDto communityRequestDto =CommunityRequestDto.builder()
            .contents("")
            .build();

        assertThrows(CustomException.class, ()-> commentService.createCommunityComments(12345677775L, communityRequestDto, u));
        assertThrows(CustomException.class, ()-> commentService.updateCommunityComments(12345677775L, communityRequestDto, u));
    }
}
