package com.project.sparta.user.service;


import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletResponse;

public interface KakaoService {
    String kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException;
    String getToken(String code) throws JsonProcessingException;
}
