package com.project.sparta.hashtag.controller;


import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.service.HashtagService;
import com.project.sparta.security.UserDetailImpl;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;

@Controller
@RequiredArgsConstructor
public class HashtagController {
   private final HashtagService hashtagService;

   // 해시태그 추가
   @PostMapping("/hashtags")
   public ResponseEntity createHashtag(@RequestBody String value, @AuthenticationPrincipal UserDetailImpl userDetail) {
       Hashtag hashtag = hashtagService.createHashtag(value, userDetail.getUser());
       HashtagResponseDto hashtagResponseDto = new HashtagResponseDto(hashtag.getId(), hashtag.getName());
       return new ResponseEntity(hashtagResponseDto, HttpStatus.OK);
   }

   //해시태그 삭제
   @DeleteMapping("/hashtags/{id}")
   public ResponseEntity deleteHashtag(@RequestBody Long id, @AuthenticationPrincipal UserDetailImpl userDetail) {
       hashtagService.deleteHashtag(id, userDetail.getUser());
       return new ResponseEntity(HttpStatus.OK);
   }

   //해시태그 전체 조회
   @GetMapping("/hashtags")
   public ResponseEntity getHashtagList(@RequestBody int offset, int limit, @AuthenticationPrincipal UserDetailImpl userDetail) {
       hashtagService.getHashtagList(offset, limit, userDetail.getUser());
       return new ResponseEntity(HttpStatus.OK);
   }

   //디폴트 해시태그 전체 조회
   @GetMapping("hashtags/default")
   public ResponseEntity getFixedHashtagList() {
       List<HashtagResponseDto> fixedHashtagList = hashtagService.getFixedHashtagList();
       return new ResponseEntity<>(fixedHashtagList, HttpStatus.OK);
   }
}


