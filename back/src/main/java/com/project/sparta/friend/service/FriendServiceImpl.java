package com.project.sparta.friend.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.friend.dto.FriendInfoReponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.hashtag.repository.HashtagRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserTag;
import com.project.sparta.user.repository.UserRepository;
import com.project.sparta.user.repository.UserTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.sparta.exception.api.Status.NOT_FOUND_HASHTAG;
import static com.project.sparta.exception.api.Status.NOT_FOUND_USER;
import static com.project.sparta.user.entity.StatusEnum.USER_REGISTERED;
import static com.project.sparta.exception.api.Status.CONFLICT_FRIEND;
import static com.project.sparta.exception.api.Status.INVALID_USER;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    private final HashtagRepository hashtagRepository;
    private final UserTagRepository userTagRepository;

    //내 친구목록 전체조회
    @Transactional(readOnly = true)
    @Override
    public PageResponseDto<List<FriendInfoReponseDto>> AllMyFriendList(int page, int size,
        Long userId) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        List<FriendInfoReponseDto> friendInfoList = new ArrayList<>();

        Page<Friend> friendsList = friendRepository.findAllByUserId(userId, pageRequest);

        for (Friend friend : friendsList) {
            User friendInfo = userRepository.findById(friend.getTargetId())
                .orElseThrow(() -> new CustomException(INVALID_USER));

            List<Hashtag> tagList = userTagRepository.findUserTag(friendInfo.getId());

            friendInfoList.add(
                new FriendInfoReponseDto(friendInfo.getId(), friendInfo.getUserImageUrl(),
                    friendInfo.getNickName(), tagList));
        }
        long totalCount = friendInfoList.size();

        return new PageResponseDto(page, totalCount, friendInfoList);
    }

    //회원의 태그 선택 기준으로 추천 친구목록 조회
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<FriendInfoReponseDto>> AllRecomentFriendList(int page, int size,
        Long userId) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        List<FriendInfoReponseDto> content = new ArrayList<>();

        //해당 유저의 회원가입 태그 정보 긁어오기
        User userInfo = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(INVALID_USER));

        //매칭 회원 랜덤으로 뽑아오기
        Page<User> randomList = friendRepository.randomUser(userInfo, pageRequest, USER_REGISTERED);

        for (User target : randomList.toList()) {
            //매칭된 회원의 태그 리스트 뽑기
            List<Hashtag> tagList = userTagRepository.findUserTag(target.getId());

            content.add(new FriendInfoReponseDto(target.getId(), target.getUserImageUrl(), target.getNickName(), tagList));
        }

        long totalCount = content.size();

        return new PageResponseDto(page, totalCount, content.stream().distinct().collect(Collectors.toList()));
    }

    //친구 추가
    @Override
    @Transactional
    public void addFriend(Long userId, Long targetId) {

        //이미 친구로 등록되어있는지 확인
        Friend check = friendRepository.findByUserIdAndTargetId(userId, targetId);

        //친구 추가
        if (check == null) {
            friendRepository.saveAndFlush(new Friend(userId, targetId));
        } else {
            throw new CustomException(CONFLICT_FRIEND);
        }
    }

    //친구 삭제
    @Override
    @Transactional
    public void deleteFriend(Long userId, Long targetId) {

        //삭제할 친구 정보 있는지 확인
        User userInfo = userRepository.findById(targetId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        //내 친구 목록에서 요청한 친구 있는지 확인
        Friend friend = friendRepository.findByTargetIdAndUserId(userInfo.getId(), userId);

        if (friend.equals(null)) {
            throw new CustomException(INVALID_USER);
        }
        friendRepository.deleteById(friend.getId());
    }

    //친구 검색(내친구 포함 모든친구)
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<FriendInfoReponseDto>> searchFriend(int page, int size,
        String targetUserName) {

        //page , size 값 임의로 넣기
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));

        //친구 이름으로 검색(+with paging 처리)
        Page<User> user = friendRepository.serachFriend(targetUserName, pageRequest);
        List<FriendInfoReponseDto> content = new ArrayList<>();

        for (User u : user.toList()) {
            List<Hashtag> tagList = userTagRepository.findUserTag(u.getId());
            content.add(new FriendInfoReponseDto(user.toList().get(0).getId(),
                user.toList().get(0).getUserImageUrl(), user.toList().get(0).getNickName(),
                tagList));
        }
        long totalCount = content.size();

        //리스트 반환
        return new PageResponseDto(page, totalCount,
            content.stream().distinct().collect(Collectors.toList()));
    }
}
