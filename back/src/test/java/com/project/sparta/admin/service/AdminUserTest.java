package com.project.sparta.admin.service;

import static com.project.sparta.user.entity.StatusEnum.USER_WITHDRAWAL;
import static com.project.sparta.user.entity.UserGradeEnum.MOUNTAIN_GOD;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.admin.dto.UserStatusDto;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.user.dto.UserListResponseDto;
import com.project.sparta.user.dto.UserOneResponseDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@AutoConfigureMockMvc
public class AdminUserTest {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

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
  @DisplayName(value = "어드민 회원 탈퇴/복구 처리")
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

    String randomUser2 = "user" + UUID.randomUUID();
    String randomUser3 = "user" + UUID.randomUUID();
    String randomUser4 = "user" + UUID.randomUUID();

    //user 생성
    User user1 = User.userBuilder()
        .email(randomUser2)
        .password("user1234!")
        .nickName("내일은매니아")
        .age(25)
        .phoneNumber("010-1234-1235")
        .userImageUrl("USER.JPG")
        .build();

    User user2 = User.userBuilder()
        .email(randomUser3)
        .password("user1234!")
        .nickName("내일은매니아2")
        .age(25)
        .phoneNumber("010-1234-1235")
        .userImageUrl("USER.JPG")
        .build();

    User user3 = User.userBuilder()
        .email(randomUser4)
        .password("user1234!")
        .nickName("내일은매니아3")
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
