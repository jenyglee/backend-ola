package com.project.sparta.user.service;

import com.project.sparta.user.dto.UserLoginDto;
import com.project.sparta.user.dto.UserResponseDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRoleEnum login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUserName(userLoginDto.getUserName()).orElseThrow(()-> new IllegalArgumentException("회원이 존재하지 않습니다.")); //나중에 exception 처리 다시 해야함

        if(!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); //나중에 exception 처리 다시 해야함
        }
        return user.getRole();
    }
}
