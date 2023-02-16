package com.project.sparta.like.service;

import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.like.entity.CourseLike;
import com.project.sparta.like.repository.LikeRecommendRepository;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendCourseLikeServiceImpl implements RecommendCourseLikeService{

    private final RecommendCourseBoardRepository courseRepository;
    private final LikeRecommendRepository likeRecommendRepository;


    public void likeRecommendCourse(Long id, User user){
        RecommendCourseBoard recommendCourseBoard  = courseRepository.findById(id).orElseThrow(()->new CustomException(Status.NOT_FOUND_POST));
        //레파지토리에서 이메일로 좋아요 있는지 없는지 찾아서 없으면
        Optional<CourseLike> findByUserEmail = likeRecommendRepository.findByUserEmailAndCourseBoard(user.getEmail(),recommendCourseBoard);

        if(findByUserEmail.isPresent()) throw new CustomException(Status.CONFLICT_LIKE);

        //보드라이크 생성
        CourseLike courseLike = CourseLike.builder()
                .userNickName(user.getNickName())
                .userEmail(user.getEmail())
                .courseBoard(recommendCourseBoard)
                .build();

        //레파지토리에 저장

        likeRecommendRepository.save(courseLike);


    }
    public void unLikeRecommendCourse(Long id, User user){
        //아이디값으로 보드 찾고
        RecommendCourseBoard recommendCourseBoard  = courseRepository.findById(id).orElseThrow(()->new CustomException(Status.NOT_FOUND_POST));
        //라이크 레파지토리에서 이메일로 찾고이미 누른 좋아요라면
        CourseLike findByUserEmail = likeRecommendRepository.findByUserEmailAndCourseBoard(user.getEmail(),recommendCourseBoard).orElseThrow(()->new CustomException(Status.CONFLICT_LIKE));
        //레파지토리에서 좋아요를 삭제한다.
        likeRecommendRepository.delete(findByUserEmail);

    }




}
