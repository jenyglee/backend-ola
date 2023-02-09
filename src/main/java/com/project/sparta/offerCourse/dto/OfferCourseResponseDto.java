package com.project.sparta.offerCourse.dto;

import com.project.sparta.admin.entity.StatusEnum;
import com.project.sparta.offerCourse.entity.OfferCourseImg;
import lombok.Getter;

import java.util.List;

@Getter
public class OfferCourseResponseDto {
    private List<String> imgList;

    public OfferCourseResponseDto(List<String> imgList) {
        this.imgList = imgList;
    }
}
