package com.project.sparta.admin.service.impl;

import static com.project.sparta.exception.api.Status.*;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.admin.service.AdminService;
import com.project.sparta.exception.CustomException;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import java.util.Optional;
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
    public Long signup(AdminSignupDto adminRequestSignupDto) {
        // 관리자 비밀번호 잘못 입력
        if(!adminRequestSignupDto.getAdminToken().equals(ADMIN_TOKEN)){
            throw new CustomException(INVALID_ADMIN_TOKEN);
        }
        // 이미 존재하는 이메일
        Optional<User> sameEmail = userRepository.findByEmail(adminRequestSignupDto.getEmail());
        if(sameEmail.isPresent()){
            throw new CustomException(CONFLICT_EMAIL);
        }
        //이미 존재하는 닉네임
        Optional<User> sameNickname = userRepository.findByNickName(
            adminRequestSignupDto.getNickName());
        if(sameNickname.isPresent()){
            throw new CustomException(CONFLICT_NICKNAME);
        }

        User admin = User.adminBuilder()
            .email(adminRequestSignupDto.getEmail())
            .password(encoder.encode(adminRequestSignupDto.getPassword()))
            .nickName(adminRequestSignupDto.getNickName())
            .build();
        userRepository.save(admin);
        return admin.getId();
    }
}

