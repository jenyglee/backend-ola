package com.project.sparta.admin.controller;


import com.project.sparta.admin.dto.AdminRequestSignupDto;
import com.project.sparta.admin.service.AdminService;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.offerCourse.dto.RequestRecommandCoursePostDto;
import com.project.sparta.offerCourse.dto.ResponseFindAllRecommandCouesePostDto;
import com.project.sparta.offerCourse.dto.ResponseOnePostDto;
import com.project.sparta.offerCourse.dto.ResponseRecommandCourseImgDto;
import com.project.sparta.offerCourse.service.RecommandCoursePostService;
import com.project.sparta.security.UserDetailsImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import com.project.sparta.admin.service.AdminServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.project.sparta.admin.dto.ManagerPersonResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AdminController {
    private final AdminService adminService;
    private final CommunityBoardService communityBoardService;
    private final RecommandCoursePostService recommandCoursePostService;
    @GetMapping("/get/one/person")
    public ResponseEntity getOnePerson(@PathVariable Long id)
    {
        ManagerPersonResponseDto managerPersonResponseDto = adminService.getOneUser(id);
        return new ResponseEntity<>(managerPersonResponseDto,HttpStatus.OK);
    }


    // 어드민 회원가입
    @PostMapping("/signup/admin")
    public ResponseEntity signup(@RequestBody AdminRequestSignupDto signupDto){
        adminService.signup(signupDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/community_boards/{community_board_id}")
    public ResponseEntity getCommunityBoard(@PathVariable Long community_board_id) {
        CommunityBoardResponseDto communityBoardResponseDto = communityBoardService.getCommunityBoard(community_board_id);
        return new ResponseEntity<>(communityBoardResponseDto,HttpStatus.OK);
    }
    @GetMapping("/community_boards")
    public ResponseEntity getAllCommunityBoard(
        @RequestParam("page") int page,
        @RequestParam("size") int size) {

        List<CommunityBoardResponseDto> communityBoardResponseDto = communityBoardService.getAllCommunityBoard(page,size);
        return new ResponseEntity<>(communityBoardResponseDto,HttpStatus.OK);
    }
    @PatchMapping("/community_boards/{community_board_id}")
    public ResponseEntity updateCommunityBoard(@PathVariable Long community_board_id, @RequestBody CommunityBoardRequestDto communityBoardRequestDto
        ,@AuthenticationPrincipal UserDetailsImpl userDetail) {
        CommunityBoardResponseDto communityBoardResponseDto = communityBoardService.updateCommunityBoard(
            community_board_id, communityBoardRequestDto, userDetail.getUser());
        return new ResponseEntity<>(communityBoardResponseDto, HttpStatus.OK);
    }
    //선택한 게시글 삭제
    @DeleteMapping("/community_boards/{community_board_id}")
    public ResponseEntity deleteCommunityBoard(@PathVariable Long community_board_id) {
        communityBoardService.deleteCommunityBoard(community_board_id);
        return new ResponseEntity("보드 삭제 완료", HttpStatus.OK);
    }

    @PutMapping("admin/api/OfferCourse/{id}")
    public ResponseRecommandCourseImgDto modifyOfferCourse(@PathVariable Long id, @RequestPart(value="image", required=false) List<MultipartFile> imges,
        @RequestPart(value = "requestDto") RequestRecommandCoursePostDto requestDto) throws IOException {

        List<String> imgRouteList = recommandCoursePostService.modifyRecommandCoursePost(id,imges, requestDto);

        return new ResponseRecommandCourseImgDto(imgRouteList);
    }

    @DeleteMapping("admin/api/OfferCourse/{id}")
    public void deleteOfferCourse(@PathVariable Long id){
        recommandCoursePostService.deleteRecommandCoursePost(id);
    }

    //단건조회
    @GetMapping("admin/api/OfferCourse/{id}")
    public ResponseOnePostDto oneSelectOfferCoursePost(@PathVariable Long id){
        return recommandCoursePostService.oneSelectRecommandCoursePost(id);
    }

    //전체조회
    @GetMapping("admin/api/OfferCourse")
    public PageResponseDto<List<ResponseFindAllRecommandCouesePostDto>> allOfferCousePost(@RequestParam int offset, @RequestParam int limit){

        return recommandCoursePostService.allRecommandCousePost(offset-1, limit);
    }


}
