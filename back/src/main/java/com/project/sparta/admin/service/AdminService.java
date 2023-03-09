package com.project.sparta.admin.service;

import com.project.sparta.admin.dto.AdminSignupDto;

public interface AdminService {
  Long signup(AdminSignupDto signupDto);
}