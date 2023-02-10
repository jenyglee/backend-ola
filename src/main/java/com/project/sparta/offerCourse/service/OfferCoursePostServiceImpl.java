package com.project.sparta.offerCourse.service;


import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.offerCourse.dto.RequestOfferCoursePostDto;
import com.project.sparta.offerCourse.dto.ResponseOnePostDto;
import com.project.sparta.offerCourse.entity.OfferCourseImg;
import com.project.sparta.offerCourse.entity.OfferCoursePost;
import com.project.sparta.offerCourse.entity.PostStatusEnum;
import com.project.sparta.offerCourse.repository.OfferCoursePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    /**
     * 추천코스 게시글 등록 메서드
     * @param imges: 글쓸때 입력한 이미지들
     * @param requestPostDto:타이틀, 컨텐츠
     * @return 이미지경로 URL 리턴
     * @throws IOException
     */
    @Override
    public List<String> creatOfferCoursePost(List<MultipartFile> imges, RequestOfferCoursePostDto requestPostDto) throws IOException {
        //Dto에서 데이터 빼기

        //이미지등록 서비스 활용해서 이미지 리스트 바꾸기
        List<OfferCourseImg> imgList = offerCourseImgService.createImgList(imges);

        //post 빌드패턴으로 생성하기 (포스트 부분 빌드패턴 부분에 for문으로 집어넣는거 구현하자)
        OfferCoursePost post = OfferCoursePost.builder()
                .title(requestPostDto.getTitle())
                .contents(requestPostDto.getContents())
                .postStatus(PostStatusEnum.VAILABLE)
                        .build();

        //이미지에 포스트 담아주고
        for (OfferCourseImg image:imgList) {
            image.addPost(post);
        }
        // 스트림으로 이미지 각각의 경로를 뽑아내서
        List<String> imgRouteList = imgList.stream().map(OfferCourseImg::getImgRoute).collect(Collectors.toList());

        //레파지토리에 저장하기.
        offerCoursePostRepository.save(post);

        //반환값 이미지 리스트 URL
        return imgRouteList;
    }

    /**
     * 코스추천 글수정 메서드
     * @param id : 선택한 게시글 id값
     * @param imges : 입력한 이미지 리스트
     * @param requestPostDto : 타이틀, 컨텐츠
     * @return 이미지 리스트 URL 리턴함
     * @throws IOException
     */

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
        //이미지에 포스트 담아주고
        for (OfferCourseImg image:imgList) {
            image.addPost(post);
        }

        // 스트림으로 이미지 각각의 경로를 뽑아내서
        List<String> imgRouteList = imgList.stream().map(OfferCourseImg::getImgRoute).collect(Collectors.toList());

        //포스트 다시 세이브 하면 수정 로직 완료
        offerCoursePostRepository.save(post);

        return imgRouteList;
    }

    /**
     * 코스추천 삭제 메서드
     * @param id : 삭제할 게시글 아이디
     *
     */

    //코스추천 삭제
    @Override
    public void deleteOfferCoursePost(Long id){
        //포스트 아이디 조회해서 포스트 아이디 있는지 검증
        OfferCoursePost post = offerCoursePostRepository.findById(id)
                .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));
        //포스트의 스테이터스 값 변경하고 다시 저장
        post.statusModifyOfferCousePost(PostStatusEnum.UNVAILABLE);
        offerCoursePostRepository.save(post);

    }

    //단건 코스 조회
    @Override
    public ResponseOnePostDto oneSelectOfferCoursePost(Long id){
        //1. 아이디값으로 포스트 찾아서 포스트 만들고
        OfferCoursePost offerCoursePost = offerCoursePostRepository.findById(id).orElseThrow();

        //2. 포스트 안에 들어있는 리스트 뽑아서 String<list>로 만들어줌
        List<String> imgRouteList = offerCoursePost.getImages().stream()
                .map(OfferCourseImg::getImgRoute).collect(Collectors.toList());

        //3. 리스폰스엔티티에 담아서 클라이언트에게 응답
        return new ResponseOnePostDto(offerCoursePost,imgRouteList);

    }


    //전체 코스 조회(페이징)

    public void allOfferCousePost(int offset, int limit,String userName){
        //1.페이징으로 요청해서
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<OfferCoursePost> all = offerCoursePostRepository.findAll(pageRequest);
        //2.전체 데이터 뽑아서
        List<OfferCoursePost> content = all.getContent();
        long totalElements = all.getTotalElements();
        //3. 엔티티로 만들어서
        // 필요한 데이터가.. 총 갯수, 타이틀, 이미지, 좋아요 갯수 (잠시만 이미지는 어떻게 하지..? 이미지없으면 디폴트 이미지를 어떻게 설정해주지..?(프엔영역인가..))
        //4. 클라이언트에 응답.

    }

    // 필터링 도전



}
