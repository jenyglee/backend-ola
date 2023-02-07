package com.project.sparta.friend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendSearchResultDto <T>{

    private int currentPage;

    private Long count;

    private T data;
}
