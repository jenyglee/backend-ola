package com.project.sparta.offerCourse.controller;

import com.project.sparta.offerCourse.dto.ResponseOfferCourseDto;
import com.project.sparta.offerCourse.dto.RequestOfferCoursePostDto;
import com.project.sparta.offerCourse.service.OfferCoursePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferCourseContoroller {


    private final OfferCoursePostService offerCoursePostService;

    @PostMapping("admin/api/creatOfferCourse")
    @ResponseStatus(HttpStatus.OK)
    public ResponseOfferCourseDto creatOfferCourse(@RequestPart(value="image", required=false) List<MultipartFile> imges,
                                                   @RequestPart(value = "requestDto") RequestOfferCoursePostDto requestDto) throws IOException {

        List<String> imgRouteList = offerCoursePostService.creatOfferCoursePost(imges, requestDto);


        return new ResponseOfferCourseDto(imgRouteList);
    }


    @PutMapping("admin/api/creatOfferCourse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseOfferCourseDto modifyOfferCourse(@PathVariable Long id, @RequestPart(value="image", required=false) List<MultipartFile> imges,
                                                    @RequestPart(value = "requestDto") RequestOfferCoursePostDto requestDto) throws IOException {

        List<String> imgRouteList = offerCoursePostService.modifyOfferCoursePost(id,imges, requestDto);

        return new ResponseOfferCourseDto(imgRouteList);
    }

    @DeleteMapping("admin/api/creatOfferCourse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOfferCourse(@PathVariable Long id){
        offerCoursePostService.deleteOfferCoursePost(id);
    }

}
