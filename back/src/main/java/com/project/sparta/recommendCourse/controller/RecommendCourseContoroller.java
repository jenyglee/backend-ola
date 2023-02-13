package com.project.sparta.recommendCourse.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.recommendCourse.dto.ResponseFindAllRecommendCouesePostDto;
import com.project.sparta.recommendCourse.dto.ResponseRecommendCourseImgDto;
import com.project.sparta.recommendCourse.dto.RequestRecommendCoursePostDto;
import com.project.sparta.recommendCourse.dto.ResponseOnePostDto;
import com.project.sparta.recommendCourse.service.RecommendCoursePostService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendCourseContoroller {


    private final RecommendCoursePostService recommendCoursePostService;


    /**
     *
     *
     * @param imges:산     이미지
     * @param requestDto: (String title,int score,String season,int altitude,String contents)
     * @param userDetail : 글작성하는 유저정보
     * @return imgList : imgURL List
     * @throws IOException
     */

    //Todo API명세서 나오면 API URL 수정
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('MOUNTAIN_GOD')")
    @PostMapping("admin/api/OfferCourse")
    public ResponseRecommendCourseImgDto creatOfferCourse(@RequestPart(value = "image", required = false) List<MultipartFile> imges,
                                                          @RequestPart(value = "requestDto") RequestRecommendCoursePostDto requestDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetail) throws IOException {

        Long userId = userDetail.getUser().getId();
        List<String> imgRouteList = recommendCoursePostService.creatRecomendCoursePost(imges, requestDto,userId);
        return new ResponseRecommendCourseImgDto(imgRouteList);

    }


    /**
     * 코스추천 글 수정 api
     * @param id :수정할 글 번호
     * @param imges : 수정할 이미지
     * @param requestDto :수정할 글 데이터
     * @param userDetail :수정하는 유저정보
     * @return
     * @throws IOException
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('MOUNTAIN_GOD')")
    @PutMapping("admin/api/OfferCourse/{id}")
    public ResponseRecommendCourseImgDto modifyOfferCourse(@PathVariable Long id, @RequestPart(value = "image", required = false) List<MultipartFile> imges,
                                                           @RequestPart(value = "requestDto") RequestRecommendCoursePostDto requestDto,
                                                           @AuthenticationPrincipal UserDetailsImpl userDetail) throws IOException {

        User user = userDetail.getUser();
        List<String> imgRouteList = recommendCoursePostService.modifyRecommendCoursePost(id, imges, requestDto, user.getId());

        return new ResponseRecommendCourseImgDto(imgRouteList);
    }

    /**
     * 코스추천 글삭제 api
     * @param id : 삭제할 글번호
     * @param userDetail : 삭제하는 유저정보
     */

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('MOUNTAIN_GOD')")
    @DeleteMapping("admin/api/OfferCourse/{id}")
    public void deleteOfferCourse(@PathVariable Long id,
                                  @AuthenticationPrincipal UserDetailsImpl userDetail) {
        User user = userDetail.getUser();
        recommendCoursePostService.deleteRecommendCoursePost(id,user.getId());
    }

    //단건조회
    @GetMapping("admin/api/OfferCourse/{id}")
    public ResponseOnePostDto oneSelectOfferCoursePost(@PathVariable Long id) {
        return recommendCoursePostService.oneSelectRecommendCoursePost(id);
    }

    //전체조회
    @GetMapping("admin/api/OfferCourse")
    public PageResponseDto<List<ResponseFindAllRecommendCouesePostDto>> allOfferCousePost(@RequestParam int offset, @RequestParam int limit) {

        return recommendCoursePostService.allRecommendCousePost(offset - 1, limit);
    }

}
