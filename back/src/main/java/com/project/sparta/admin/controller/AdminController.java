package com.project.sparta.admin.controller;


import com.project.sparta.admin.dto.AdminSignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.project.sparta.admin.service.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AdminController {
    private final AdminService adminService;

    // 어드민 회원가입
    @PostMapping("/signup/admin")
    public ResponseEntity signup(@RequestBody AdminSignupDto signupDto){
        adminService.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
