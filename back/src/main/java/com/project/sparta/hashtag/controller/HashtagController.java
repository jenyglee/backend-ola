package com.project.sparta.hashtag.controller;


import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.service.HashtagService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

   //해시태그 추가
   // @PostMapping("/hashtags")
   // public ResponseEntity createHashtag(@RequestBody String value
   //                                     // @AuthenticationPrincipal UserDetailImpl userDetail
   // ) {
   //     User user1 = new User("user1@naver.com", "1234", "이재원", UserRoleEnum.USER, USER_REGISTERED, 10, "010-1234-1234", "sdf.jpg",
   //             UserGradeEnum.MOUNTAIN_GOD);
   //     Hashtag hashtag = hashtagService.createHashtag(value, user1);
   //     HashtagResponseDto hashtagResponseDto = new HashtagResponseDto(hashtag.getId(), hashtag.getName());
   //     return new ResponseEntity(hashtagResponseDto, HttpStatus.OK);
   // }
   //
   // //해시태그 삭제
   // @DeleteMapping("/hashtags/{id}")
   // public ResponseEntity deleteHashtag(@RequestBody Long id
   //                                     // @AuthenticationPrincipal UserDetailImpl userDetail
   // ) {
   //     User user1 = new User("user1@naver.com", "1234", "이재원", UserRoleEnum.USER, USER_REGISTERED, 10, "010-1234-1234", "sdf.jpg",
   //             UserGradeEnum.MOUNTAIN_GOD);
   //     hashtagService.deleteHashtag(id, user1);
   //     return new ResponseEntity(HttpStatus.OK);
   // }
   //
   // //해시태그 전체 조회
   // @GetMapping("/hashtags")
   // public ResponseEntity getHashtagList(@RequestBody int offset, int limit
   //                                      // @AuthenticationPrincipal UserDetailImpl userDetail
   // ) {
   //     User user1 = new User("user1@naver.com", "1234", "이재원", UserRoleEnum.USER, USER_REGISTERED, 10, "010-1234-1234", "sdf.jpg",
   //             UserGradeEnum.MOUNTAIN_GOD);
   //     hashtagService.getHashtagList(offset, limit, user1);
   //     return new ResponseEntity(HttpStatus.OK);
   // }
   //
   // //디폴트 해시태그 전체 조회
   // @GetMapping("hashtags/default")
   // public ResponseEntity getFixedHashtagList() {
   //     List<HashtagResponseDto> fixedHashtagList = hashtagService.getFixedHashtagList();
   //     return new ResponseEntity<>(fixedHashtagList, HttpStatus.OK);
   // }
}


