package com.project.sparta.user.service;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.admin.dto.UserStatusDto;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.security.dto.TokenDto;
import com.project.sparta.user.dto.InfoResponseDto;
import com.project.sparta.user.dto.LoginRequestDto;
import com.project.sparta.user.dto.UpgradeRequestDto;
import com.project.sparta.user.dto.UserListResponseDto;
import com.project.sparta.user.dto.UserOneResponseDto;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.dto.ValidateEmailDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static com.project.sparta.user.entity.StatusEnum.USER_REGISTERED;
import static com.project.sparta.user.entity.StatusEnum.USER_WITHDRAWAL;
import static com.project.sparta.user.entity.UserGradeEnum.MOUNTAIN_GOD;
import static com.project.sparta.user.entity.UserGradeEnum.MOUNTAIN_MANIA;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    private String randomUser1 = "user" + UUID.randomUUID();

    private List taglist = java.util.Arrays.asList(1L, 2L, 3L, 4L);

    @Test
    @Transactional
    @DisplayName(value = "회원가입")
    void signup() {

        UserSignupDto user1 = UserSignupDto.builder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("기똥차게올라가보자")
            .age(25)
            .phoneNumber("010-1234-1235")
            .imageUrl("user.jpg")
            .tagList(taglist)
            .build();

        userService.signup(user1);

        User user = userRepository.findByNickName("기똥차게올라가보자").orElseThrow(() -> new CustomException(
            Status.NOT_FOUND_USER));

        Assertions.assertThat(user.getNickName()).isEqualTo(user1.getNickName());
    }

    @Test
    @Transactional
    @DisplayName(value = "나의 정보 조회")
    void getMyInfo() {
        ArrayList<User> userList = createUser();
        InfoResponseDto infoResponseDto = userService.getMyInfo(userList.get(0));
        Assertions.assertThat(infoResponseDto.getNickName())
            .isEqualTo(userList.get(0).getNickName());
        Assertions.assertThat(infoResponseDto.getUserGradeEnum())
            .isEqualTo(userList.get(0).getGradeEnum());
    }

    @Test
    @Transactional
    @DisplayName(value = "자동등업")
    void upgrade() {
        ArrayList<User> userList = createUser();
        UpgradeRequestDto upgradeRequestDto = new UpgradeRequestDto("MANIA");

        userService.upgrade(upgradeRequestDto, userList.get(0).getId());

        User user = userRepository.findById(userList.get(0).getId())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_USER));

        Assertions.assertThat(user.getGradeEnum()).isEqualTo(MOUNTAIN_MANIA);
    }

    @Test
    @Transactional
    @DisplayName(value = "어드민용 회원 단건 조회")
    void getUser() {
        ArrayList<User> userList = createUser();
        UserOneResponseDto userOneResponseDto = userService.getUser(userList.get(0).getId());
        Assertions.assertThat(userOneResponseDto.getNickName())
            .isEqualTo(userList.get(0).getNickName());
        Assertions.assertThat(userOneResponseDto.getUserGradeEnum())
            .isEqualTo(userList.get(0).getGradeEnum());
        Assertions.assertThat(userOneResponseDto.getAge()).isEqualTo(userList.get(0).getAge());
    }

    @Test
    @Transactional
    @DisplayName(value = "어드민용 회원 전체 조회")
    void getUserList() {
        Long userCnt = userRepository.count();
        PageResponseDto<List<UserListResponseDto>> userAllList = userService.getUserList(0, 5);

        Assertions.assertThat(userAllList.getTotalCount()).isEqualTo(userCnt);
    }

    @Test
    @Transactional
    @DisplayName(value = "어드민용 회원 등급 변경")
    void changeGrade() {
        ArrayList<User> userList = createUser();
        UserGradeDto upgradeRequestDto = new UserGradeDto(2);

        userService.changeGrade(upgradeRequestDto, userList.get(0).getId());
        User user = userRepository.findById(userList.get(0).getId())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_USER));
        Assertions.assertThat(user.getGradeEnum()).isEqualTo(MOUNTAIN_GOD);
    }

    @Test
    @Transactional
    @DisplayName(value = "회원 탈퇴/복구 처리")
    void changeStatus() {
        ArrayList<User> userList = createUser();
        UserStatusDto statusDto = new UserStatusDto(0);
        userService.changeStatus(statusDto, userList.get(0).getId());
        User user = userRepository.findById(userList.get(0).getId())
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_USER));
        Assertions.assertThat(user.getStatus()).isEqualTo(USER_WITHDRAWAL);
    }

    @Transactional
    public ArrayList<User> createUser() {

        ArrayList<User> userList = new ArrayList<>();

        String randomUser1 = "user" + UUID.randomUUID();
        String randomUser2 = "user" + UUID.randomUUID();
        String randomUser3 = "user" + UUID.randomUUID();

        //user 생성
        User user1 = User.userBuilder()
            .email(randomUser1)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .userImageUrl("USER.JPG")
            .build();

        User user2 = User.userBuilder()
            .email(randomUser2)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .userImageUrl("USER.JPG")
            .build();

        User user3 = User.userBuilder()
            .email(randomUser3)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .userImageUrl("USER.JPG")
            .build();

        User u = userRepository.save(user1);
        User u2 = userRepository.save(user2);
        User u3 = userRepository.save(user3);

        userList.add(u);
        userList.add(u2);
        userList.add(u3);

        return userList;
    }
}