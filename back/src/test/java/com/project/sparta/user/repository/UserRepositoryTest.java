package com.project.sparta.user.repository;


import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.sparta.admin.dto.UserGradeDto;
import com.project.sparta.admin.dto.UserStatusDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.user.dto.UpgradeRequestDto;
import com.project.sparta.user.dto.ValidateEmailDto;
import com.project.sparta.user.dto.ValidateNickNameDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.service.UserServiceImpl;
import com.sun.xml.bind.v2.TODO;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class UserRepositoryTest {
    //TODO 테스트코드 추가 : 로그인 시 email, password 중 ""인 경우(세인)
    //TODO 테스트코드 추가 : 로그인 시 password가 틀린 경우(세인)

    @Autowired
    private UserServiceImpl userService;

    private String randomUser = "user" + UUID.randomUUID();
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("이메일 or 닉네임 중복확인")
    void duplicationCheck(){
        User user1 = User.userBuilder()
            .email(randomUser)
            .password("user1234!")
            .nickName("내일은매니아")
            .age(25)
            .phoneNumber("010-1234-1235")
            .build();

        userRepository.save(user1);

        ValidateEmailDto emailDto = new ValidateEmailDto(randomUser);
        ValidateNickNameDto nickNameDto = new ValidateNickNameDto("내일은매니아");

        assertThrows(CustomException.class, ()-> userService.validateEmail(emailDto));
        assertThrows(CustomException.class, ()-> userService.validateNickName(nickNameDto));
    }

    @Test
    @DisplayName("회원 정보를 찾을 수 없는 경우")
    @Transactional
    public void notFoundUser(){
        UpgradeRequestDto upgradeRequestDto = new UpgradeRequestDto("MANIA");
        UserGradeDto gradeDto = new UserGradeDto(2);
        UserStatusDto statusDto = new UserStatusDto(0);

        assertThrows(CustomException.class, ()-> userService.upgrade(upgradeRequestDto, 1234567891000000L));
        assertThrows(CustomException.class, ()-> userService.getUser(1234567891000000L));
        assertThrows(CustomException.class, ()-> userService.changeGrade(gradeDto, 1234567891000000L));
        assertThrows(CustomException.class, ()-> userService.changeStatus(statusDto, 1234567891000000L));
    }
}
