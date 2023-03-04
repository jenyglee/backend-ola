package com.project.sparta.communityComment.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String nickName;
    private String contents;
    private LocalDateTime createAt;
    //private Long likeCount;
    //private Boolean isLike;
    private String profileImage;

    @Builder
    public CommentResponseDto(Long id, String nickName, String contents, LocalDateTime createAt
        //, Long likeCount
        //, Boolean isLike
        , String profileImage) {
        this.id = id;
        this.nickName = nickName;
        this.contents = contents;
        this.createAt = createAt;
        //this.likeCount = likeCount;
        //this.isLike = isLike;
        this.profileImage = profileImage;
    }
}
