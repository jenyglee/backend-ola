package com.project.sparta.alarm.service;


import com.project.sparta.alarm.dto.AlarmRequetDto;
import com.project.sparta.alarm.dto.AlarmResponseDto;
import com.project.sparta.alarm.entity.Alarm;
import com.project.sparta.alarm.repository.AlarmRespository;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AlarmServiceImplTest {

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private AlarmRespository alarmRespository;

    @Test
    @Transactional
    @DisplayName(value = "알람 추가")
    void createAlarm() {
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
            .boardId(communityBoard.getId())
            .boardType("community")
            .build();

        alarmService.createAlarm(alarmRequetDto, userList.get(1).getNickName());
        alarmService.createAlarm(alarmRequetDto, userList.get(2).getNickName());

        ArrayList<Alarm> alarms = alarmRespository.findByBoardId(communityBoard.getId());
        Assertions.assertThat(alarms.get(0).getWriterNickName()).isEqualTo(userList.get(1).getNickName());
    }

    @Test
    @Transactional
    @DisplayName(value = "나의 알람 리스트 조회")
    void getMyAlarmList() {
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
            .boardId(communityBoard.getId())
            .boardType("community")
            .build();

        alarmService.createAlarm(alarmRequetDto, userList.get(1).getNickName());
        alarmService.createAlarm(alarmRequetDto, userList.get(2).getNickName());

        PageResponseDto<List<AlarmResponseDto>> alarmList = alarmService.getMyAlarmList(userList.get(0), 0, 5);
        Assertions.assertThat(alarmList.getTotalCount()).isEqualTo(2);
    }

    @Test
    @Transactional
    @DisplayName(value = "알람 상태값 변경")
    void updateAlarmStatus() {
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
            .boardId(communityBoard.getId())
            .boardType("community")
            .build();

        alarmService.createAlarm(alarmRequetDto, userList.get(1).getNickName());
        alarmService.createAlarm(alarmRequetDto, userList.get(2).getNickName());

        ArrayList<Alarm> alarms = alarmRespository.findByBoardId(communityBoard.getId());

        alarmService.updateAlarmStatus(alarms.get(0).getUserId(), alarms.get(0).getId());
        Assertions.assertThat(alarms.get(0).getReadStatus()).isEqualTo("Y");
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