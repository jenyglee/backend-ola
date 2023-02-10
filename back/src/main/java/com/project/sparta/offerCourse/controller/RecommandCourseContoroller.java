package com.project.sparta.offerCourse.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.offerCourse.dto.ResponseFindAllRecommandCouesePostDto;
import com.project.sparta.offerCourse.dto.ResponseRecommandCourseImgDto;
import com.project.sparta.offerCourse.dto.RequestRecommandCoursePostDto;
import com.project.sparta.offerCourse.dto.ResponseOnePostDto;
import com.project.sparta.offerCourse.service.RecommandCoursePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommandCourseContoroller {


    private final RecommandCoursePostService recommandCoursePostService;

    //Todo API명세서 나오면 API URL 수정
    @PostMapping("admin/api/OfferCourse")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRecommandCourseImgDto creatOfferCourse(@RequestPart(value="image", required=false) List<MultipartFile> imges,
                                                          @RequestPart(value = "requestDto") RequestRecommandCoursePostDto requestDto) throws IOException {

        List<String> imgRouteList = recommandCoursePostService.creatRecomandCoursePost(imges, requestDto);


        return new ResponseRecommandCourseImgDto(imgRouteList);
    }


    @PutMapping("admin/api/OfferCourse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseRecommandCourseImgDto modifyOfferCourse(@PathVariable Long id, @RequestPart(value="image", required=false) List<MultipartFile> imges,
                                                           @RequestPart(value = "requestDto") RequestRecommandCoursePostDto requestDto) throws IOException {

        List<String> imgRouteList = recommandCoursePostService.modifyRecommandCoursePost(id,imges, requestDto);

        return new ResponseRecommandCourseImgDto(imgRouteList);
    }

    @DeleteMapping("admin/api/OfferCourse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOfferCourse(@PathVariable Long id){
        recommandCoursePostService.deleteRecommandCoursePost(id);
    }

    //단건조회
    @GetMapping("admin/api/OfferCourse/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseOnePostDto oneSelectOfferCoursePost(@PathVariable Long id){
        return recommandCoursePostService.oneSelectRecommandCoursePost(id);
    }

    //전체조회
    @GetMapping("admin/api/OfferCourse")
    @ResponseStatus(HttpStatus.OK)
    public PageResponseDto<List<ResponseFindAllRecommandCouesePostDto>> allOfferCousePost(@RequestParam int offset, @RequestParam int limit){

        return recommandCoursePostService.allRecommandCousePost(offset-1, limit);
    }


}
