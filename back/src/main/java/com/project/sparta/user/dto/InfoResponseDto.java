package com.project.sparta.user.dto;


import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InfoResponseDto {
    private Long communityCount;
    private Long recommendCount;
    private int enterCount;
    private int makeCount;
    private List<HashtagResponseDto> tagList;

    @Builder
    public InfoResponseDto(Long communityCount, Long recommendCount, int enterCount, int makeCount,
        List<HashtagResponseDto> tagList) {
        this.communityCount = communityCount;
        this.recommendCount = recommendCount;
        this.enterCount = enterCount;
        this.makeCount = makeCount;
        this.tagList = tagList;
    }
}
