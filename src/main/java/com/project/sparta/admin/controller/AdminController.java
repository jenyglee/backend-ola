package com.project.sparta.admin.controller;

import com.project.sparta.admin.dto.RequestOfferCoursePostDto;
import com.project.sparta.admin.service.OfferCourseImgService;
import com.project.sparta.admin.service.OfferCoursePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final OfferCoursePostService offerCoursePostService;

    @PostMapping("admin/api/creatOfferCourse")
    public void creatOfferCourse( @RequestPart(value="image", required=false) List<MultipartFile> imges,
                                  @RequestPart(value = "requestDto") RequestOfferCoursePostDto requestDto) throws IOException {

        offerCoursePostService.creatOfferCoursePost(imges,requestDto);




    }
}
