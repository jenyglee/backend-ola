package com.project.sparta.user.service;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.project.sparta.security.dto.TokenDto;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface KakaoService {
    ResponseEntity<TokenDto> kakaoLogin(String code) throws JsonProcessingException;
    String getToken(String code) throws JsonProcessingException;
}
