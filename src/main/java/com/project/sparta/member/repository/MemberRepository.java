package com.project.sparta.member.repository;


import com.project.sparta.member.dto.MemberResponseDto;

public interface MemberRepository {

    MemberResponseDto findByUsername(String username);
}
