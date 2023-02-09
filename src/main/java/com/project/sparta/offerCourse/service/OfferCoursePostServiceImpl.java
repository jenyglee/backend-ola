package com.project.sparta.offerCourse.service;


import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.offerCourse.dto.RequestOfferCoursePostDto;
import com.project.sparta.offerCourse.entity.OfferCourseImg;
import com.project.sparta.offerCourse.entity.OfferCoursePost;
import com.project.sparta.offerCourse.repository.OfferCoursePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OfferCoursePostServiceImpl implements OfferCoursePostService{

    private final OfferCoursePostRepository offerCoursePostRepository;

    private final OfferCourseImgService offerCourseImgService;
    //코스 등록
    @Override
    public List<String> creatOfferCoursePost(List<MultipartFile> imges, RequestOfferCoursePostDto requestPostDto) throws IOException {
        //Dto에서 데이터 빼기

        //이미지등록 서비스 활용해서 이미지 리스트 바꾸기
        List<OfferCourseImg> imgList = offerCourseImgService.createImgList(imges);

        //post 빌드패턴으로 생성하기 (포스트 부분 빌드패턴 부분에 for문으로 집어넣는거 구현하자)
        OfferCoursePost post = OfferCoursePost.builder()
                .title(requestPostDto.getTitle())
                .contents(requestPostDto.getContents())
                        .build();

        for (OfferCourseImg image:imgList) {
            image.addPost(post);
        }

        List<String> imgRouteList = imgList.stream().map(OfferCourseImg::getImgRoute).collect(Collectors.toList());

        //레파지토리에 저장하기.
        offerCoursePostRepository.save(post);

        //반환값 이미지 리스트 URL
        return imgRouteList;
    }

    //코스 수정
    @Override
    public List<String> modifyOfferCoursePost(Long id, List<MultipartFile> imges, RequestOfferCoursePostDto requestPostDto) throws IOException {
        // Dto로 수정할 제목이랑 텍스트랑 이미지리스트 받아오고 주소에서 아이디값 받아와서
        OfferCoursePost post = offerCoursePostRepository.findById(id)
                .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));
        // 변경 메서드 하나 만든다음에
        post.modifyOfferCousePost(requestPostDto.getTitle(), requestPostDto.getContents());
        // 받아온 이미지 파일을 다시 리스트로 변경하고
        List<OfferCourseImg> imgList = offerCourseImgService.createImgList(imges);

        for (OfferCourseImg image:imgList) {
            image.addPost(post);
        }

        List<String> imgRouteList = imgList.stream().map(OfferCourseImg::getImgRoute).collect(Collectors.toList());

        //포스트 다시 세이브 하면 수정 로직 완료
        offerCoursePostRepository.save(post);

        return imgRouteList;
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
