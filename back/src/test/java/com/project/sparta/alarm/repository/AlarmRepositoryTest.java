package com.project.sparta.alarm.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.sparta.alarm.dto.AlarmRequetDto;
import com.project.sparta.alarm.entity.Alarm;
import com.project.sparta.alarm.service.AlarmService;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AlarmRepositoryTest {


    @Autowired
    private AlarmService alarmService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;


    @Test
    @Transactional
    @DisplayName(value = "댓글 달린 Board를 찾을 수 없는 경우")
    void notFoundBoard() {
        ArrayList<User> userList = createUser();

        CommunityBoard board = CommunityBoard.builder()
            .title("내일 산악회 정모 한번 가시죠")
            .contents("내일 아침 9시어떠세요?")
            .chatStatus("N")
            .chatMemCnt(0)
            .user(userList.get(0))
            .maniaResponse(0)
            .godResponse(0)
            .build();

        CommunityBoard communityBoard = boardRepository.save(board);

        AlarmRequetDto alarmRequetDto = AlarmRequetDto.builder()
            .message("xxx님이 내일 매니아 글에 댓글을 달았습니다")
            .boardId(10052222200000L)
            .boardType("community")
            .build();

        assertThrows(CustomException.class, ()-> alarmService.createAlarm(alarmRequetDto, userList.get(1).getNickName()));
        assertThrows(CustomException.class, ()-> alarmService.updateAlarmStatus(1234567899L, 5555888888L));
    }
    
    @Transactional
    public ArrayList<User> createUser(){

        ArrayList<User> userList = new ArrayList<>();

        String randomUser1 = "user" + UUID.randomUUID();
        String randomUser2 = "user" + UUID.randomUUID();
        String randomUser3 = "user" + UUID.randomUUID();

        //user 생성
        User user = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        User user2 = User.userBuilder()
            .email(randomUser2)
            .password("user1234!")
            .nickName("오늘은 등린이")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();


        User user3 = User.userBuilder()
            .email(randomUser3)
            .password("user1234!")
            .nickName("건강을위해서")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        User u = userRepository.save(user);
        User u2 = userRepository.save(user2);
        User u3 = userRepository.save(user3);

        userList.add(u);
        userList.add(u2);
        userList.add(u3);

        return userList;
    }

}
