package com.project.sparta.admin.service;


import com.project.sparta.admin.dto.RequestOfferCoursePostDto;
import com.project.sparta.admin.entity.OfferCourseImg;
import com.project.sparta.admin.entity.OfferCoursePost;
import com.project.sparta.admin.repository.OfferCoursePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferCoursePostServiceImpl implements OfferCoursePostService{

    private final OfferCoursePostRepository offerCoursePostRepository;

    private final OfferCourseImgService offerCourseImgService;
    //코스 등록
    @Override
    public void creatOfferCoursePost(List<MultipartFile> imges, RequestOfferCoursePostDto requestPostDto) throws IOException {
        //Dto에서 데이터 빼기

        //이미지등록 서비스 활용해서 이미지 리스트 바꾸기
        List<OfferCourseImg> imgList = offerCourseImgService.createImgList(imges);

        //post 빌드패턴으로 생성하기 (포스트 부분 빌드패턴 부분에 for문으로 집어넣는거 구현하자)
        OfferCoursePost post = OfferCoursePost.builder()
                .title(requestPostDto.getTitle())
                .contents(requestPostDto.getContents())
                .images(imgList)
                        .build();

        //레파지토리에 저장하기.
        offerCoursePostRepository.save(post);
    }

    //코스 수정
    @Override
    public void modifyOfferCoursePost(){
        // Dto로 수정할 텍스트 받아와서
        //

    }

    //코스 삭제
    @Override
    public void deleteOfferCoursePost(){
        //포스트 아이디 조회해서 포스트 아이디 있는지 검증
        //딜리트

    }

    //단건 코스 조회
    //코스 등록
    //코스 수정
    //코스 삭제
    // 필터링 도전



}
