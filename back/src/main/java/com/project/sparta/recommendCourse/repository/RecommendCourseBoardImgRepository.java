package com.project.sparta.recommendCourse.repository;

import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendCourseBoardImgRepository extends JpaRepository<RecommendCourseImg,Long> {

    @Query("delete from RecommendCourseImg r where r.recommendCourseBoard.id=:boardId")
    void deleteBoard(Long boardId);
}
