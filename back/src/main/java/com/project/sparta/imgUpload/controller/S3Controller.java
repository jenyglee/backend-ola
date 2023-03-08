package com.project.sparta.imgUpload.controller;


import com.project.sparta.imgUpload.dto.PreSignedURLResponseDto;
import com.project.sparta.imgUpload.service.S3UploadService;
import com.project.sparta.security.UserDetailsImpl;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {
    private final S3UploadService s3UploadService;

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */

//    @PostMapping("/file")
//    public ResponseEntity uploadFile(
//            @RequestParam(value = "multipartFile") List<MultipartFile> multipartFile) {
//        List<String> imgList = s3UploadService.uploadFile(multipartFile);
//        System.out.println(imgList.get(0).toString());
//        return new ResponseEntity(imgList, HttpStatus.OK);
//    }

    @PostMapping("/preSignedURL")
    public PreSignedURLResponseDto GeneratePreSignedURL(@RequestBody String fileName){

        PreSignedURLResponseDto preSignedURL = s3UploadService.signBucket(fileName);

        return preSignedURL;
    }

    @GetMapping("/getPreSignedURL")
    public String GetPreSignedURL(@RequestParam("fileName") String fileName){

        String getPreSignedURL = s3UploadService.getPresignedURL(fileName);

        return getPreSignedURL;
    }
}
