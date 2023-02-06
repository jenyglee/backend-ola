package com.project.sparta.member.controller;

import com.project.sparta.member.dto.MemberLoginDto;
import com.project.sparta.member.entity.MemberRoleEnum;
import com.project.sparta.member.repository.MemberRepository;
import com.project.sparta.member.service.MemberService;
import com.project.sparta.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
    private final MemberService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public HttpStatus login(@RequestBody MemberLoginDto memberLoginDto, HttpServletResponse response){
        MemberRoleEnum role = userService.login(memberLoginDto);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(memberLoginDto.getUsername(), role));
        return HttpStatus.OK;
    }
}
