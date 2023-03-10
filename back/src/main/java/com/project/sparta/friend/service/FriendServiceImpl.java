package com.project.sparta.friend.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.friend.dto.FriendInfoReponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.repository.UserTagRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.sparta.exception.api.Status.NOT_FOUND_USER;
import static com.project.sparta.user.entity.StatusEnum.USER_REGISTERED;
import static com.project.sparta.exception.api.Status.CONFLICT_FRIEND;
import static com.project.sparta.exception.api.Status.INVALID_USER;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;

    //내 친구목록 전체조회
    @Override
    public PageResponseDto<List<FriendInfoReponseDto>> AllMyFriendList(int page, int size,
        Long userId) {
        // 1. user ID를 포함하여 내 친구 전체조회
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Friend> friendsList = friendRepository.findAllByUserId(userId, pageRequest);

        // 2.내 친구의 정보를 DTO로 변환하여 친구 리스트에 저장
        List<FriendInfoReponseDto> friendInfoList = new ArrayList<>();
        friendsList.stream().forEach(friend -> {
            // 1. 친구의 정보를 조회
            User friendInfo = userRepository.findById(friend.getTargetId())
                .orElseThrow(() -> new CustomException(INVALID_USER));

            // 2. 친구의 해시태그를 조회
            List<Hashtag> tagList = userTagRepository.findUserTag(friendInfo.getId());

            // 3. 내 친구 리스트에 추가
            friendInfoList.add(
                new FriendInfoReponseDto(friendInfo.getId(), friendInfo.getUserImageUrl(),
                    friendInfo.getNickName(), tagList));
        });

        long totalCount = friendInfoList.size();
        return new PageResponseDto(page, totalCount, friendInfoList);
    }

    // 회원의 태그 선택 기준으로 추천 친구목록 조회
    @Override
    public PageResponseDto<List<FriendInfoReponseDto>> AllRecommendFriendList(int page, int size,
        Long userId) {
        // 1. user ID를 이용해 매칭 회원 리스트를 랜덤으로 추출
        User userInfo = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(INVALID_USER));
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<User> randomList = friendRepository.randomUser(userInfo, pageRequest, USER_REGISTERED);

        // 2. 랜덤 추천친구 리스트를 DTO로 변환하여 저장
        List<FriendInfoReponseDto> friendInfos = new ArrayList<>();
        randomList.stream().forEach(target -> {
            // 추천 친구의 태그 리스트를 추출
            List<Hashtag> tagList = userTagRepository.findUserTag(target.getId());
            friendInfos.add(new FriendInfoReponseDto(target.getId(), target.getUserImageUrl(),
                target.getNickName(), tagList));
        });

        long totalCount = friendInfos.size();
        return new PageResponseDto(page, totalCount,
            friendInfos.stream().distinct().collect(Collectors.toList()));
    }

    //친구 추가
    @Override
    @Transactional
    public void addFriend(Long userId, Long targetId) {
        // 1. 이미 친구로 등록되어있는지 확인
        Optional<Friend> friend = friendRepository.findByUserIdAndTargetId(userId, targetId);
        if (friend.isPresent()) {
            throw new CustomException(CONFLICT_FRIEND);
        }

        // 2. 친구 추가
        friendRepository.saveAndFlush(new Friend(userId, targetId));
    }

    //친구 삭제
    @Override
    @Transactional
    public void deleteFriend(Long userId, Long targetId) {
        // 1. 삭제할 친구 정보 있는지 확인
        User userInfo = userRepository.findById(targetId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        // 2. 내 친구 목록에 있는지 확인
        Friend friend = friendRepository.findByTargetIdAndUserId(userInfo.getId(), userId)
            .orElseThrow(() -> new CustomException(INVALID_USER));

        // 3. 친구 삭제
        friendRepository.deleteById(friend.getId());
    }

    // 친구 검색(내친구 포함 모든친구)
    @Override
    public PageResponseDto<List<FriendInfoReponseDto>> searchFriend(int page, int size,
        String targetUserName) {
        // 1. 친구 이름으로 검색
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<User> users = friendRepository.serachFriend(targetUserName, pageRequest);

        // 2. 검색된 회원을 DTO로 변환하여 리스트에 저장
        List<FriendInfoReponseDto> friendInfoList = new ArrayList<>();
        users.stream().forEach(user -> {
            // 검색된 회원의 태그 리스트 추출
            List<Hashtag> tagList = userTagRepository.findUserTag(user.getId());
            friendInfoList.add(
                new FriendInfoReponseDto(user.getId(), user.getUserImageUrl(), user.getNickName(),
                    tagList));
        });

        long totalCount = friendInfoList.size();

        return new PageResponseDto(page, totalCount,
            friendInfoList.stream().distinct().collect(Collectors.toList()));
    }
}
