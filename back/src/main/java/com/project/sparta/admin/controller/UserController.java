package com.project.sparta.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {
    @GetMapping("/users")
    public void getUserList(){

    }

    @GetMapping("/users/{id}")
    public void getUser(){

    }

    @PatchMapping("/users/{id}")
    public void updateUser(){

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(){

    }
}
