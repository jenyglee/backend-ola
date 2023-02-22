package com.project.sparta.imgUpload.controller;


import com.project.sparta.imgUpload.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class S3Controller {
    private final S3UploadService s3UploadService;

    /**
     * Amazon S3에 파일 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */

    @PostMapping("/file")
    public ResponseEntity uploadFile(
            @RequestParam(value = "multipartFile") List<MultipartFile> multipartFile) {
        List<String> imgList = s3UploadService.uploadFile(multipartFile);
        System.out.println(imgList.get(0).toString());
        return new ResponseEntity(imgList, HttpStatus.OK);
    }

}
