package com.project.sparta.recommendCourse.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendImgResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.service.RecommendCoursePostService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendCourseController {
    private final RecommendCoursePostService recommendCoursePostService;
    /**
     *
     *
     * @param images:산     이미지
     * @param requestDto: (String title,int score,String season,int altitude,String contents)
     * @param userDetail : 글작성하는 유저정보
     * @return imgList : imgURL List
     * @throws IOException
     */

    //Todo API명세서 나오면 API URL 수정
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_GOD')")
    @PostMapping("admin/api/OfferCourse")
    public ResponseEntity creatOfferCourse(@RequestPart(value = "image", required = false) List<MultipartFile> images,
                                           @RequestPart(value = "requestDto") RecommendRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetail) throws IOException {
        Long userId = userDetail.getUser().getId();
        recommendCoursePostService.creatRecommendCoursePost(images, requestDto, userId);
        return new ResponseEntity(HttpStatus.OK);

        // 이미지업로드 API를 쏴서 이미지를 백에다가 업로드요청을 합니다.
        // 백은 이미지를 받아서 DB에 저장하고 DB에 저장된 이미지의 URL을 이미지업로드API에 반환해줍니다.
        // 클라이언트에서는 이미지업로드API에서 반환받은 이미지의 URL을 코스추천 작성API에 포함해서 요청합니다.
        // 백은 코스추천 API에서 요청받은 타이틀, 컨텐츠, 이미지URL을 코스추천 DB에 저장합니다.
        // 반환할건 없습니다.
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_CHILDREN')")
    @PutMapping("admin/api/OfferCourse/{id}")
    public RecommendImgResponseDto modifyOfferCourse(@PathVariable Long id, @RequestPart(value = "image", required = false) List<MultipartFile> imges,
                                                     @RequestPart(value = "requestDto") RecommendRequestDto requestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetail) throws IOException {

        User user = userDetail.getUser();
        List<String> imgRouteList = recommendCoursePostService.modifyRecommendCoursePost(id, imges, requestDto, user.getId());

        return new RecommendImgResponseDto(imgRouteList);
    }

    /**
     * 코스추천 글삭제 api
     * @param id : 삭제할 글번호
     * @param userDetail : 삭제하는 유저정보
     */

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_CHILDREN')")
    @DeleteMapping("admin/api/OfferCourse/{id}")
    public void deleteOfferCourse(@PathVariable Long id,
                                  @AuthenticationPrincipal UserDetailsImpl userDetail) {
        User user = userDetail.getUser();
        recommendCoursePostService.deleteRecommendCoursePost(id,user.getId());
    }

    //단건조회
    @GetMapping("admin/api/OfferCourse/{id}")
    public RecommendDetailResponseDto oneSelectOfferCoursePost(@PathVariable Long id) {
        return recommendCoursePostService.oneSelectRecommendCoursePost(id);
    }

    //전체조회
    @GetMapping("admin/api/OfferCourse")
    public PageResponseDto<List<RecommendResponseDto>> allOfferCousePost(@RequestParam int offset, @RequestParam int limit) {

        return recommendCoursePostService.allRecommendCousePost(offset - 1, limit);
    }

}
