package com.project.sparta.hashtag.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.hashtag.dto.HashtagRequestDto;
import com.project.sparta.hashtag.dto.HashtagResponseDto;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.sparta.exception.api.Status.*;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;

    //해시태그 추가
    @Override
    public Hashtag createHashtag(String value, User user) {
        // 에러1: 이름이 ""인 경우
        if(value.isBlank()){
            throw new CustomException(INVALID_HASHTAG_NAME);
        }
        // 에러2: 중복된 이름이 있는경우
        Optional<Hashtag> findHashtag = hashtagRepository.findByName(value);
        if(findHashtag.isPresent()){
            throw new CustomException(INVALID_HASHTAG_NAME);
        }

        Hashtag hashtag = new Hashtag(value);
        return hashtagRepository.save(hashtag);
    }


    //해시태그 삭제
    @Override
    public void deleteHashtag(Long id, User user) {
        Hashtag hashtag = hashtagRepository.findById(id).orElseThrow(
                ()-> new CustomException(NOT_FOUND_HASHTAG));
        hashtagRepository.delete(hashtag);
    }

    //해시태그 전체 조회
    @Override
    public PageResponseDto<List<HashtagResponseDto>> getHashtagList(int offset, int limit, User user) {
        // 1. 페이징으로 요청해서 조회
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Hashtag> results = hashtagRepository.findAll(pageRequest);

        // 2. 데이터, 전체 개수 추출
        List<Hashtag> hashtagList = results.getContent();
        long totalElements = results.getTotalElements();

        // 3. 엔티티를 DTO로 변환
        List<HashtagResponseDto> hashtagResponseDtoList = new ArrayList<>();
        for (Hashtag hashtag : hashtagList) {
            HashtagResponseDto hashtagResponseDto = new HashtagResponseDto(hashtag.getId(), hashtag.getName());
            hashtagResponseDtoList.add(hashtagResponseDto);
        }

        //4. 클라이언트에 응답(현재페이지, 전체 건수, 데이터 포함)
        return new PageResponseDto<>(offset, totalElements, hashtagResponseDtoList);
    }

    //디폴트 해시태그 전체 조회
    @Override
    public List<HashtagResponseDto> getFixedHashtagList() {
        List<Hashtag> hashtagList = hashtagRepository.findAll();

        List<HashtagResponseDto> hashtagResponseDtoList = new ArrayList<>();
        for (Hashtag hashtag : hashtagList) {
            HashtagResponseDto hashtagResponseDto = new HashtagResponseDto(hashtag.getId(), hashtag.getName());
            hashtagResponseDtoList.add(hashtagResponseDto);
        }

        return hashtagResponseDtoList;
    }
}
