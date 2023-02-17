package com.project.sparta.recommendCourse.service;


import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.GetMyBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.like.repository.LikeRecommendRepository;
import com.project.sparta.recommendCourse.dto.GetMyRecommendCourseResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.entity.PostStatusEnum;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendCourseBoardServiceImpl implements RecommendCourseBoardService {

    private final RecommendCourseBoardRepository recommendCourseBoardRepository;
    private final UserRepository userRepository;
    private final LikeRecommendRepository likeRecommendRepository;


    /**
     * 추천코스 게시글 등록 메서드
     * @param requestPostDto:(String title,int score,String season,int altitude,String contents)
     * @return 이미지경로 URL 리턴
     * @throws IOException
     */
    @Override
    public void creatRecommendCourseBoard(RecommendRequestDto requestPostDto, Long userId){
//        //이미지등록 서비스 활용해서 이미지 리스트 바꾸기
//        List<RecommendCourseImg> imgList = recommendCourseImgService.createImgList(images);

        //board 빌드패턴으로 생성하기 (포스트 부분 빌드패턴 부분에 for문으로 집어넣는거 구현하자)
        List<RecommendCourseImg> imgUrlList = new ArrayList<>(); // id, url(string)

        for (String imgUrl : requestPostDto.getImgList()) {
            RecommendCourseImg recommendCourseImg = new RecommendCourseImg(imgUrl);
            imgUrlList.add(recommendCourseImg);
        }

        RecommendCourseBoard board = RecommendCourseBoard.builder()
                .title(requestPostDto.getTitle())
                .season(requestPostDto.getSeason())
                .altitude(requestPostDto.getAltitude())
                .contents(requestPostDto.getContents())
                .score(requestPostDto.getScore())
                .userId(userId)
                // .images(imgUrlList)
                        .build();


        //이미지에 포스트 담아주고
//        for (RecommendCourseImg image:requestPostDto.getImgList()) {
//            image.addPost(recommendCourseBoard);
//            recommendCourseImgService.saveimage(image);
//        }

//        // 스트림으로 이미지 각각의 경로를 뽑아내서
//        List<String> imgRouteList = imgList.stream().map(RecommendCourseImg::getImgRoute).collect(Collectors.toList());
        //레파지토리에 저장하기.

        recommendCourseBoardRepository.save(board);

        // ✨ s3 bucket에 있는 이미지에 Post ID 저장
    }



    /**
     * 코스추천 글수정 메서드
     * @param id : 선택한 게시글 id값
     // * @param recommendRequestDto : 타이틀, 컨텐츠
     * @return 이미지 리스트 URL 리턴함
     * @throws IOException
     */

    //코스 수정
    @Override
    public void modifyRecommendCourseBoard(Long id, RecommendRequestDto requestDto, Long userId) {
        // Dto로 수정할 제목이랑 텍스트랑 이미지리스트 받아오고 주소에서 아이디값 받아와서
        RecommendCourseBoard board = recommendCourseBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));

        if(!board.getUserId().equals(userId)) throw new CustomException(Status.NO_PERMISSIONS_POST);


//        //수정되기전의 데이터를 삭제해줘야한다. 레파지토리에서 postId값으로 이미지들을 찾아서 리스트로 넘겨준다음에 삭제해준다.
//        recommendCourseImgService.deleteImgList(id);
//
//        // 변경 메서드 하나 만든다음에 데이터값을 변경해준다.
//        board.modifyRecommendCourseBoard(recommendRequestDto,userId);

//        // 받아온 이미지 파일을 다시 리스트로 변경하고
//        List<RecommendCourseImg> imgList = recommendCourseImgService.createImgList(images);
//
//        //이미지에 포스트 담아주고
//         for (RecommendCourseImg image:recommendRequestDto.getImgList()) {
//             image.addPost(board);
//         }
        List<RecommendCourseImg> imgUrlList = new ArrayList<>();
        for (String imgUrl : requestDto.getImgList()) {
            RecommendCourseImg recommendCourseImg = new RecommendCourseImg(imgUrl);
            imgUrlList.add(recommendCourseImg);
        }

        // 스트림으로 이미지 각각의 경로를 뽑아내서
//        List<String> imgRouteList = imgList.stream().map(RecommendCourseImg::getImgRoute).collect(Collectors.toList());


        board.modifyRecommendCourseBoard(requestDto,userId, imgUrlList);

        //포스트 다시 세이브 하면 수정 로직 완료
        recommendCourseBoardRepository.save(board);
    }

    /**
     * 코스추천 삭제 메서드
     * @param id : 삭제할 게시글 아이디
     */

    //코스추천 삭제
    @Override
    public void deleteRecommendCourseBoard(Long id, Long userId){
        //포스트 아이디 조회해서 포스트 아이디 있는지 검증
        RecommendCourseBoard post = recommendCourseBoardRepository.findById(id)
                .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));

        if(!post.getUserId().equals(userId)) throw new CustomException(Status.NO_PERMISSIONS_POST);

        //포스트의 스테이터스 값 변경하고 다시 저장
        post.statusModifyRecommendCourse(PostStatusEnum.UNVAILABLE);
        recommendCourseBoardRepository.save(post);
    }

    //단건 코스 조회
    @Override
    public RecommendDetailResponseDto oneSelectRecommendCourseBoard(Long id){
        //1. 아이디값으로 포스트 찾아서 포스트 만들고
        RecommendCourseBoard recommendCourseBoard = recommendCourseBoardRepository.findById(id).orElseThrow();

        //2. 포스트 안에 들어있는 리스트 뽑아서 String<list>로 만들어줌
        List<String> dummyList = new ArrayList<>();
        // ✨ s3 bucket에서 이미지 가져오기(가져올때 id 전달)
        dummyList.add("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202302/11/9e7dd875-5cd0-4776-9ca8-264c6fdb440a.jpg");
        dummyList.add("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202302/11/9e7dd875-5cd0-4776-9ca8-264c6fdb440a.jpg");
        dummyList.add("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202302/11/9e7dd875-5cd0-4776-9ca8-264c6fdb440a.jpg");
        // List<String> imgRouteList = recommendCourseBoard.getImages().stream()
        //         .map(RecommendCourseImg::getImgRoute).collect(Collectors.toList());
        Long userId = recommendCourseBoard.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Status.NOT_FOUND_USER));
        List<String> imgRouteList = dummyList;



        //게시글의 라이크가 몇개인지 넣어준다.
        Long likeCount = likeRecommendRepository.countByCourseBoard_Id(recommendCourseBoard.getId());

        //3. 리스폰스엔티티에 담아서 클라이언트에게 응답
        return new RecommendDetailResponseDto(recommendCourseBoard, imgRouteList, user.getNickName(),likeCount);
    }

    //전체 코스 조회(페이징)

    @Override
    public PageResponseDto<List<RecommendResponseDto>> allRecommendCourseBoard(int offset, int limit){
        //1.페이징으로 요청해서
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<RecommendCourseBoard> all = recommendCourseBoardRepository.findAllBySatusIsVailable(pageRequest, PostStatusEnum.VAILABLE);

        //2.전체 데이터 뽑아서
        List<RecommendCourseBoard> contentList = all.getContent();
//        List<RecommendCourseBoard> newContentList = new ArrayList<>();
//        for (RecommendCourseBoard recommendCourseBoard : contentList) {
//            if(recommendCourseBoard.getPostStatus().equals(PostStatusEnum.VAILABLE)){
//                newContentList.add(recommendCourseBoard);
//            }
//        }

        long totalElements = all.getTotalElements();



        //3. 엔티티로 만들어서
        List<RecommendResponseDto> FindAllPostDtoList= new ArrayList<>();
        List<String> dummyList = new ArrayList<>();
        dummyList.add("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202302/11/9e7dd875-5cd0-4776-9ca8-264c6fdb440a.jpg");
        //todo 나중에 필드에 UserId 값이 아니라 user객체가 들어가도록 엔티티 수정하는게 좋을 듯.
        for (RecommendCourseBoard courseBoard:contentList) {
            User user = userRepository.findById(courseBoard.getUserId()).orElseThrow(()->new CustomException(Status.NOT_FOUND_USER));
            Long likeCount = likeRecommendRepository.countByCourseBoard_Id(courseBoard.getId());
            RecommendResponseDto responseFindAllPos = RecommendResponseDto.builder()
                    .title(courseBoard.getTitle())
                    .imgList(dummyList)
                    .likeCount(likeCount)
                    .nickName(user.getNickName())
                    .timestamped(courseBoard.getCreateAt())
                    .build();
            FindAllPostDtoList.add(responseFindAllPos);
        }


        // Todo 좋아요 갯수 필드에 추후에 추가

        //4. 클라이언트에 응답.
        return new PageResponseDto<>(offset,totalElements,FindAllPostDtoList);
    }

    //내가 쓴 게시물 조회
    @Override
    public PageResponseDto<List<GetMyRecommendCourseResponseDto>> getMyRecommendCourseBoard(int page, int size, User user){
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<RecommendCourseBoard> boards = recommendCourseBoardRepository.findByUserId(pageable, user.getId());

        List<GetMyRecommendCourseResponseDto> getMyBoardResponseDtoList = new ArrayList<>();
        for (RecommendCourseBoard recommendBoard : boards) {
            //todo userid 변수를 User 객체로 변경해야 됨
            User user1 = userRepository.findById(recommendBoard.getUserId()).orElseThrow(()->new CustomException(Status.NOT_FOUND_USER));
            Long likeCount = likeRecommendRepository.countByCourseBoard_Id(recommendBoard.getId());
            GetMyRecommendCourseResponseDto getMyBoardResponse = GetMyRecommendCourseResponseDto.builder()
                    .localDateTime(recommendBoard.getCreateAt())
                    .title(recommendBoard.getTitle())
                    .likeCount(likeCount)
                    .nickName(user1.getNickName())
                    .build();
            getMyBoardResponseDtoList.add(getMyBoardResponse);
        }
        return  new PageResponseDto<>(page, boards.getTotalElements(), getMyBoardResponseDtoList);

    }

    // 필터링 도전



}
