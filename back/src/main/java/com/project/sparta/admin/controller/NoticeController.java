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
public class NoticeController {
    @GetMapping("/boards/notices")
    public void getNoticeList(){

    }

    @GetMapping("/boards/notices/{boardId}")
    public void getNotice(){

    }

    @PatchMapping("/boards/notices/{boardId}")
    public void updateNotice(){

    }

    @DeleteMapping("/boards/notices/{boardId}")
    public void deleteNotice(){

    }
}
