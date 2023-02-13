package com.project.sparta.admin.service;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;

    public void signup(AdminSignupDto signupDto) {
        // new User(signupDto.getEmail(), signupDto.getPassword(), signupDto.getNickName(), signupDto.getAdminToken());
        // userRepository.save()
    }
}
