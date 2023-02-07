package com.project.sparta.hashtag.service;

import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;

    //해시태그 추가
    @Override
    public Hashtag createHashtag(String value, User user) {
        if(value.isBlank()){
            throw new IllegalArgumentException("해시태그 이름을 입력해주세요.");
        }
        Hashtag hashtag = new Hashtag(value);
        hashtagRepository.save(hashtag);
        return hashtag;
    }

    //해시태그 삭제
    @Override
    public void deleteHashtag(Long id, User user) {
        Hashtag hashtag = hashtagRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해시태그를 찾을 수 없습니다.")
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
