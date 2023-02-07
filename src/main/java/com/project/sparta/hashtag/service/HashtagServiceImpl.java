package com.project.sparta.hashtag.service;

import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;

    @Override
    public Hashtag createHashtag(String value, User user) {
        if(value.isBlank()){
            throw new IllegalArgumentException("해시태그 이름을 입력해주세요.");
        }
        Hashtag hashtag = new Hashtag(value);
        hashtagRepository.save(hashtag);
        return hashtag;
    }

    @Override
    public void deleteHashtag(Long id, User user) {
        Hashtag hashtag = hashtagRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해시태그를 찾을 수 없습니다.")
        );
        hashtagRepository.delete(hashtag);
    }
}
