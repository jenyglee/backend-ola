package com.project.sparta.security;

import com.project.sparta.member.dto.MemberResponseDto;
import com.project.sparta.member.entity.Member;
import com.project.sparta.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberResponseDto member = memberRepository.findByUsername(username);

        return new UserDetailImpl(member, member.getId(), member.getUsername(), member.getPassword());
    }
}
