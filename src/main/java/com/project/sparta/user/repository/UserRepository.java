package com.project.sparta.user.repository;


import com.project.sparta.user.dto.UserResponseDto;

public interface UserRepository {

    UserResponseDto findByUsername(String username);
}
