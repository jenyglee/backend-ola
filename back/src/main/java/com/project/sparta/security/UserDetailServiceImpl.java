package com.project.sparta.security;

import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //
    //     User user = userRepository.findByNickNameAndStatus(username, USER_REGISTERED).orElseThrow(()-> new IllegalArgumentException("회원이 존재하지 않습니다."));
    //
    //     return new UserDetailsImpl(user);
    // }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new UserDetailsImpl(user);
    }
}
