package com.project.sparta.hashtag.service;

import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.project.sparta.exception.api.Status.*;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;

    //해시태그 추가
    @Override
    public Hashtag createHashtag(String value, User user) {
        if(value.isBlank()){
            throw new CustomException(INVALID_HASHTAG_NAME);
        }
        Hashtag hashtag = new Hashtag(value);
        hashtagRepository.save(hashtag);
        return hashtag;
    }

    //해시태그 삭제
    @Override
    public void deleteHashtag(Long id, User user) {
        Hashtag hashtag = hashtagRepository.findById(id).orElseThrow(()->
                new CustomException(NOT_FOUND_HASHTAG)
        );
        hashtagRepository.delete(hashtag);
    }

    //해시태그 전체 조회
    @Override
    public List<HashtagResponseDto> getHashtagList(User user) {
        List<Hashtag> allHashtag = hashtagRepository.findAll();
        List<HashtagResponseDto> HashtagResponseDtoList = new ArrayList<>();
        for (Hashtag hashtag : allHashtag) {
            HashtagResponseDto hashtagResponseDto = new HashtagResponseDto(hashtag.getName());
            HashtagResponseDtoList.add(hashtagResponseDto);
        }
        return HashtagResponseDtoList;
    }
}
