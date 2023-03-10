package com.project.sparta.recommendCourse.service;


import static com.project.sparta.exception.api.Status.*;
import static com.project.sparta.recommendCourse.entity.PostStatusEnum.VAILABLE;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.like.repository.LikeRecommendRepository;
import com.project.sparta.recommendCourse.dto.RecommendCondition;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardImgRepository;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.user.entity.User;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendCourseBoardServiceImpl implements RecommendCourseBoardService {

    private final RecommendCourseBoardRepository recommendCourseBoardRepository;
    private final RecommendCourseBoardImgRepository recommendCourseBoardImgRepository;
    private final LikeRecommendRepository likeRecommendRepository;

    // 코스추천 작성
    @Override
    @Transactional
    public Long creatRecommendCourseBoard(RecommendRequestDto requestDto, Long userId) {
        // 에러1: 숫자가 null인 경우
        if (requestDto.getScore() == 0 || requestDto.getAltitude() == 0) {
            throw new CustomException(INVALID_CONTENT);
        }
        // 에러2: 컨텐츠가 null인 경우
        if (requestDto.getTitle() == null || requestDto.getSeason() == null ||
                requestDto.getContents() == null || requestDto.getRegion() == null) {
            throw new CustomException(INVALID_CONTENT);
        }

        // 에러3: Title, Season, Contents, Region 중 ""가 포함된 경우
        if (requestDto.getTitle().isBlank() || requestDto.getSeason().isBlank()
                || requestDto.getContents().isBlank() || requestDto.getRegion().isBlank()) {
            throw new CustomException(INVALID_CONTENT);
        }
        // 1. 코스추천 글 저장
        RecommendCourseBoard recommendBoard = RecommendCourseBoard.builder()
            .score(requestDto.getScore())
            .title(requestDto.getTitle())
            .season(requestDto.getSeason())
            .altitude(requestDto.getAltitude())
            .contents(requestDto.getContents())
            .region(requestDto.getRegion())
            .userId(userId)
            .postStatus(VAILABLE)
            .build();
        recommendCourseBoardRepository.save(recommendBoard);

        // 2. 코스추천의 이미지 리스트를 DB에 저장
        requestDto.getImgList().stream().forEach(imgUrl ->
            recommendCourseBoardImgRepository.save(new RecommendCourseImg(imgUrl, recommendBoard)));

        return recommendBoard.getId();
    }


    // 코스추천 수정
    @Override
    @Transactional
    public void modifyRecommendCourseBoard(Long id, RecommendRequestDto requestDto, Long userId) {


        // 에러1: 숫자가 null인 경우
        if (requestDto.getScore() == 0 || requestDto.getAltitude() == 0) {
            throw new CustomException(INVALID_CONTENT);
        }
        // 에러2: 컨텐츠가 null인 경우
        if (requestDto.getTitle() == null || requestDto.getSeason() == null ||
                requestDto.getContents() == null || requestDto.getRegion() == null) {
            throw new CustomException(INVALID_CONTENT);
        }

        // 에러3: Title, Season, Contents, Region 중 ""가 포함된 경우
        if (requestDto.getTitle().isBlank() || requestDto.getSeason().isBlank()
                || requestDto.getContents().isBlank() || requestDto.getRegion().isBlank()) {
            throw new CustomException(INVALID_CONTENT);
        }


        RecommendCourseBoard recommendBoard = recommendCourseBoardRepository.findById(id)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));

        // 에러3: 내가 작성한 글이 아닌 경우
        recommendBoard.validateOwner(userId);

        // 1. 기존 이미지 삭제 후 새로운 이미지 리스트를 저장
        recommendCourseBoardImgRepository.deleteBoard(id);
        requestDto.getImgList().stream().forEach(imgUrl ->
            recommendCourseBoardImgRepository.save(new RecommendCourseImg(imgUrl, recommendBoard)));

        // 2. 코스추천 내용 업데이트 후 저장
        recommendBoard.update(requestDto.getScore(), requestDto.getTitle(), requestDto.getSeason(),
            requestDto.getAltitude(), requestDto.getContents(), requestDto.getRegion());
        recommendCourseBoardRepository.save(recommendBoard);
    }

    // 코스추천 삭제
    @Override
    @Transactional
    public void deleteRecommendCourseBoard(Long id, Long userId) {
        RecommendCourseBoard post = recommendCourseBoardRepository.findById(id)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));

        // 에러1: 내가 작성한 글이 아닌 경우
        if (!post.getUserId().equals(userId)) {
            throw new CustomException(Status.NO_PERMISSIONS_POST);
        }

        // 1. 코스추천 이미지, 좋아요 삭제
        recommendCourseBoardImgRepository.deleteBoard(id);
        likeRecommendRepository.deleteLikeAllByInRecommendId(id);

        // 2. 코스추천 게시글 삭제
        recommendCourseBoardRepository.deleteById(post.getId());
    }

    // 코스추천 단건 조회
    @Override
    @Transactional(readOnly = true)
    public RecommendDetailResponseDto oneSelectRecommendCourseBoard(Long boardId, String nickName) {
        return recommendCourseBoardRepository.getCourseBoard(boardId, VAILABLE, nickName);
    }

    // 코스추천 전체 조회
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<RecommendResponseDto>> allRecommendCourseBoard(int page, int size,
        int score, String season, int altitude, String region, String sort) {
        // 1. 쿼리문에 적용을 위해 null값 정제화
        if (season == null) {
            season = "all";
        }
        if (region == null) {
            region = "all";
        }
        if (sort == null) {
            sort = "boardIdDesc";
        }

        // 검색조건 포함하여 전체조회
        RecommendCondition condition = new RecommendCondition(score, season, altitude, region,
            sort);
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RecommendResponseDto> courseAllList = recommendCourseBoardRepository.allRecommendBoardList(
            pageRequest, VAILABLE, condition);

        List<RecommendResponseDto> content = courseAllList.getContent();
        long totalCount = courseAllList.getTotalElements();

        return new PageResponseDto<>(page, totalCount, content);
    }

    // 내가 쓴 코스 추천 조회
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<RecommendResponseDto>> getMyRecommendCourseBoard(int page, int size,
        User user) {
        // user ID 포함하여 전체 조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RecommendResponseDto> myCourseBoardList = recommendCourseBoardRepository.myRecommendBoardList(
            pageRequest, VAILABLE, user.getId());

        List<RecommendResponseDto> content = myCourseBoardList.getContent();
        long totalCount = myCourseBoardList.getTotalElements();

        return new PageResponseDto<>(page, totalCount, content);
    }

    // 어드민 코스추천 수정
    @Override
    @Transactional
    public void adminRecommendBoardUpdate(Long id, RecommendRequestDto requestDto) {
        //수정하고자 하는 board 있는지 확인
        RecommendCourseBoard recommendBoard = recommendCourseBoardRepository.findById(id)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));

        // 1. 기존 이미지 삭제 후 새로운 이미지 리스트를 저장
        recommendCourseBoardImgRepository.deleteBoard(id);
        requestDto.getImgList().stream().forEach(imgUrl ->
            recommendCourseBoardImgRepository.save(new RecommendCourseImg(imgUrl, recommendBoard)));

        // 2. 코스추천 내용 업데이트 후 저장
        recommendBoard.update(requestDto.getScore(), requestDto.getTitle(), requestDto.getSeason(),
            requestDto.getAltitude(), requestDto.getContents(), requestDto.getRegion());
        recommendCourseBoardRepository.save(recommendBoard);
    }

    //어드민 코스추천 삭제
    @Override
    @Transactional
    public void adminRecommendBoardDelete(Long id) {
        RecommendCourseBoard post = recommendCourseBoardRepository.findById(id)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));

        // 1. 코스추천 이미지, 좋아요 삭제
        recommendCourseBoardImgRepository.deleteBoard(id);
        likeRecommendRepository.deleteLikeAllByInRecommendId(id);

        // 2. 코스추천 게시글 삭제
        recommendCourseBoardRepository.deleteById(post.getId());
    }

}
