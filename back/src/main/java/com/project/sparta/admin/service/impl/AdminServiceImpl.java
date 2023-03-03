package com.project.sparta.admin.service.impl;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.admin.service.AdminService;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.like.repository.LikeBoardRepository;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.recommendCourse.service.RecommendCourseImgService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    // 어드민 회원가입
    private final PasswordEncoder encoder;
    // 어드민 회원가입
    @Override
    public void signup(AdminSignupDto adminRequestSignupDto) {
        if(!adminRequestSignupDto.getAdminToken().equals(ADMIN_TOKEN)){
            throw new CustomException(Status.INVALID_ADMIN_TOKEN);
        }

        User admin = User.adminBuilder()
            .email(adminRequestSignupDto.getEmail())
            .password(encoder.encode(adminRequestSignupDto.getPassword()))
            .nickName(adminRequestSignupDto.getNickName())
            .build();
        userRepository.save(admin);
    }
}

