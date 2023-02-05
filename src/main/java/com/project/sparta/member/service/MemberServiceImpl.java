package com.project.sparta.member.service;

import com.project.sparta.member.dto.MemberLoginDto;
import com.project.sparta.member.dto.MemberResponseDto;
import com.project.sparta.member.entity.MemberRoleEnum;
import com.project.sparta.member.repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepositoryImpl memberRepositoryImpl;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberRoleEnum login(MemberLoginDto memberLoginDto) {
        MemberResponseDto member = memberRepositoryImpl.findByUsername(memberLoginDto.getUsername()); //나중에 null 일때 exception 처리 다시 해야함

        if(!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); //나중에 exception 처리 다시 해야함
        }
        return member.getMemberRoleEnum();
    }
}
