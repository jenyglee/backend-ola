package com.project.sparta.security;

import com.project.sparta.user.dto.UserResponseDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByNickName(username).orElseThrow(()-> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return new UserDetailImpl(user, user.getId(), user.getNickName(), user.getPassword());
    }
}
