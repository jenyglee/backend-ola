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
import org.springframework.transaction.annotation.Transactional;

import static com.project.sparta.exception.api.Status.*;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;

    //해시태그 추가
    @Override
    @Transactional
    public Hashtag createHashtag(String name, User user) {
        // 에러1: 이름이 ""인 경우
        if (name.isBlank()) {
            throw new CustomException(INVALID_HASHTAG_NAME);
        }
        // 에러2: 중복된 이름이 있는경우
        Optional<Hashtag> findHashtag = hashtagRepository.findByName(name);
        if (findHashtag.isPresent()) {
            throw new CustomException(INVALID_HASHTAG_NAME);
        }

        Hashtag hashtag = new Hashtag(name);
        return hashtagRepository.save(hashtag);
    }


    //해시태그 삭제
    @Override
    @Transactional
    public void deleteHashtag(Long id, User user) {
        Hashtag hashtag = hashtagRepository.findById(id).orElseThrow(
            () -> new CustomException(NOT_FOUND_HASHTAG));
        hashtagRepository.delete(hashtag);
    }

    //해시태그 전체 조회
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<HashtagResponseDto>> getHashtagList(int offset, int limit,
        String name) {
        // 1. name을 포함하여 전체 조회
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Hashtag> results = hashtagRepository.findAllByLikeName(name, pageRequest);

        // 2. 해시태그 리스트를 DTO로 변환하여 리스트에 저장
        List<HashtagResponseDto> hashtagResponseDtoList = getHashtagResponseDtos(results.toList());

        long totalElements = results.getTotalElements();
        return new PageResponseDto<>(offset, totalElements, hashtagResponseDtoList);
    }

    //디폴트 해시태그 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<HashtagResponseDto> getFixedHashtagList() {
        // 1. 전체 해시태그 조회
        List<Hashtag> hashtagList = hashtagRepository.findAll();
        // 2. 해시태그 리스트를 DTO로 변환하여 리스트에 저장
        List<HashtagResponseDto> hashtagResponseDtoList = getHashtagResponseDtos(hashtagList);
        return hashtagResponseDtoList;
    }

    private static List<HashtagResponseDto> getHashtagResponseDtos(List<Hashtag> results) {
        List<HashtagResponseDto> hashtagResponseDtoList = new ArrayList<>();
        results.stream().forEach(hashtag -> {
            HashtagResponseDto hashtagResponseDto = new HashtagResponseDto(hashtag.getId(),
                hashtag.getName());
            hashtagResponseDtoList.add(hashtagResponseDto);
        });
        return hashtagResponseDtoList;
    }
}
