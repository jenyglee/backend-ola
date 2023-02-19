package com.project.sparta.admin.controller;

import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {

    // TODO 어드민 User API 제작
    @GetMapping("/users")
    public void getUserList(){

    }

    @GetMapping("/users/{userId}")
    public void getUser(){

    }

    @PatchMapping("/users/{userId}")
    public void updateUser(){

    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(){

    }
}
