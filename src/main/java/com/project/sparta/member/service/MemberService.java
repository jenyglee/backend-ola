package com.project.sparta.member.service;


import com.project.sparta.member.dto.MemberLoginDto;
import com.project.sparta.member.entity.MemberRoleEnum;

public interface MemberService {

    MemberRoleEnum login(MemberLoginDto memberLoginDto);
}
