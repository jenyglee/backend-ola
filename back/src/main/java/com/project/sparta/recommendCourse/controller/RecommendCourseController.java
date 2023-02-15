package com.project.sparta.recommendCourse.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.recommendCourse.dto.ImgUrlDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import com.project.sparta.recommendCourse.service.RecommendCourseImgService;
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
    private final RecommendCourseBoardService recommendCourseBoardService;
    private final RecommendCourseImgService recommendCourseImgService;


    //이미지 업로드 api
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_GOD')")
    @PostMapping("/recommend_image") //todo url 어떤것으로 할지 의논 후 수정
    public ImgUrlDto ImageUpload(@RequestPart(value = "image", required = false) List<MultipartFile> images)throws IOException{
        recommendCourseImgService.createImgList(images);
        String aa =  "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202302/11/9e7dd875-5cd0-4776-9ca8-264c6fdb440a.jpg";
        ImgUrlDto dto = new ImgUrlDto(aa);
        return dto;
        // url 아무거나 일단 리턴하도록 수정하기
    }


    /**
     * 글작성 api
     * @param requestDto: (String title,int score,String season,int altitude,String contents)
     * @param userDetail : 글작성하는 유저정보
     * @return imgList : imgURL List
     * @throws IOException
     */

    //Todo API명세서 나오면 API URL 수정
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_GOD')")
    @PostMapping("/recommend_boards")
    public ResponseEntity createRecommendCourse(@RequestBody RecommendRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetail){
        Long userId = userDetail.getUser().getId();
        recommendCourseBoardService.creatRecommendCourseBoard(requestDto, userId);

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
     * @param requestDto :수정할 글 데이터
     * @param userDetail :수정하는 유저정보
     * @return
     * @throws IOException
     */
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_CHILDREN')")
    @PutMapping("admin/api/OfferCourse/{id}")
    public ResponseEntity modifyRecommendCourse(@RequestBody RecommendRequestDto requestDto,
                                                @PathVariable Long id,
                                                @AuthenticationPrincipal UserDetailsImpl userDetail) {

        User user = userDetail.getUser();
        recommendCourseBoardService.modifyRecommendCourseBoard(id, requestDto, user.getId());

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 코스추천 글삭제 api
     * @param id : 삭제할 글번호
     * @param userDetail : 삭제하는 유저정보
     */

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER:GRADE_CHILDREN')")
    @DeleteMapping("admin/api/OfferCourse/{id}")
    public void deleteRecommendCourse(@PathVariable Long id,
                                      @AuthenticationPrincipal UserDetailsImpl userDetail) {
        User user = userDetail.getUser();
        recommendCourseBoardService.deleteRecommendCourseBoard(id,user.getId());
    }

    //단건조회
    @GetMapping("admin/api/OfferCourse/{id}")
    public RecommendDetailResponseDto oneRecommendCourse(@PathVariable Long id) {
        return recommendCourseBoardService.oneSelectRecommendCourseBoard(id);
    }

    //전체조회
    @GetMapping("admin/api/OfferCourse")
    public PageResponseDto<List<RecommendResponseDto>> allRecommendCourse(@RequestParam int offset, @RequestParam int limit) {

        return recommendCourseBoardService.allRecommendCourseBoard(offset, limit);
    }

}