package com.project.sparta.admin.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.user.repository.UserRepository;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.project.sparta.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class AdminUserTest {
  @Autowired
  AdminService adminService;

  @Autowired
  UserRepository userRepository;

  String randomUser = "user"+ UUID.randomUUID() +"@naver.com";

  @Test
  @Transactional
  public void signUp(){
    //given
    AdminSignupDto signupDto = AdminSignupDto
        .builder()
        .email(randomUser)
        .password("1234")
        .nickName("하이")
        .adminToken("AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC")
        .build();

    //when
    Long adminId = adminService.signup(signupDto);
    User user1 = userRepository.findById(adminId).orElseThrow();

    //then
    assertThat(user1.getNickName()).isEqualTo("하이");
  }

}
