package com.project.sparta.hashtag.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.hashtag.dto.HashtagRequestDto;
import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.user.entity.User;

import java.util.List;

public interface HashtagService {
    Hashtag createHashtag(String value, User user);
    void deleteHashtag(Long id, User user);
    PageResponseDto<List<HashtagResponseDto>> getHashtagList(int offset, int limit, User user);
    List<HashtagResponseDto> getFixedHashtagList();
}
