package com.project.sparta.hashtag.controller;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.service.HashtagService;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = {"해쉬태그 API"})
@Controller
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;

    // 해시태그 추가
    @ApiOperation(value = "해시태그 추가", response = Join.class)
    @PostMapping("/hashtags")
    public ResponseEntity createHashtag(@RequestParam String name,
        @AuthenticationPrincipal UserDetailsImpl userDetail) {
        Hashtag hashtag = hashtagService.createHashtag(name, userDetail.getUser());
        HashtagResponseDto hashtagResponseDto = new HashtagResponseDto(hashtag.getId(),
            hashtag.getName());
        return new ResponseEntity(hashtagResponseDto, HttpStatus.OK);
    }

    //해시태그 삭제
    @ApiOperation(value = "해시태그 삭제", response = Join.class)
    @DeleteMapping("/hashtags/{hashtagId}")
    public ResponseEntity deleteHashtag(@RequestBody Long hashtagId,
        @AuthenticationPrincipal UserDetailsImpl userDetail) {
        hashtagService.deleteHashtag(hashtagId, userDetail.getUser());
        return new ResponseEntity(HttpStatus.OK);
    }

    //해시태그 전체 조회
    @ApiOperation(value = "해시태그 전체 조회", response = Join.class)
    @GetMapping("/hashtags")
    public ResponseEntity getHashtagList(@RequestParam int offset, @RequestParam int limit,
        @RequestParam String name,
        @AuthenticationPrincipal UserDetailsImpl userDetail) {
        PageResponseDto<List<HashtagResponseDto>> result = hashtagService.getHashtagList(
            offset, limit, name, userDetail.getUser());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    //기본 해시태그 조회
    @ApiOperation(value = "기본 해시태그 조회", response = Join.class)
    @GetMapping("hashtags/default")
    public ResponseEntity getFixedHashtagList() {
        List<HashtagResponseDto> fixedHashtagList = hashtagService.getFixedHashtagList();
        return new ResponseEntity<>(fixedHashtagList, HttpStatus.OK);
    }
}


