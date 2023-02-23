package com.project.sparta.recommendCourse.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecommendCondition {

    private int score;
    private String season;
    private int altitude;
    private String region;
    private String orderByLike;

    public RecommendCondition(int score, String season, int altitude, String region, String orderByLike) {
        this.score = score;
        this.season = season;
        this.altitude = altitude;
        this.region = region;
        this.orderByLike = orderByLike;
    }
}
