package com.project.sparta.admin.service;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;

    // 어드민 회원가입
    public void signup(AdminSignupDto signupDto) {
        if(!signupDto.getAdminToken().equals(ADMIN_TOKEN)){
            throw new CustomException(Status.INVALID_ADMIN_TOKEN);
        }
        User admin = User.adminBuilder()
                .email(signupDto.getEmail())
                .password(signupDto.getPassword())
                .nickName(signupDto.getNickName())
                .build();
        userRepository.save(admin);
    }

}
