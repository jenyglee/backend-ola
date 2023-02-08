package com.project.sparta.friend.service;

import com.project.sparta.friend.dto.FriendSearchReponseDto;
import com.project.sparta.friend.dto.FriendSearchResultDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //내 친구목록 조회

    //친구 추가
    @Override
    @Transactional
    public void addFriend(Long userId, String targetUsername) {
        User targetUser = userRepository.findByUserName(targetUsername).orElseThrow(()->new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        friendRepository.saveAndFlush(new Friend(userId, targetUser.getId()));
    }

    //친구 삭제
    @Override
    @Transactional
    public void deleteFriend(String targetUsername) {
        User targetUser = userRepository.findByUserName(targetUsername).orElseThrow(()->new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        if(targetUser.equals(null)){
            throw new IllegalArgumentException("회원정보를 찾을 수 없습니다.");
        }
        friendRepository.deleteById(targetUser.getId());
    }

    //친구 검색
    @Override
    @Transactional(readOnly = true)
    public FriendSearchResultDto searchFriend(int offset, int limit, String targetUserName) {

        //offset , limit 값 임의로 넣기
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

        //친구 이름으로 검색(+with paging 처리)
        Page<User> user = friendRepository.serachFriend(targetUserName, pageRequest);

        //검색했을 경우에 프로필 사진, 이름 뽑아서 보여주기
        Page<FriendSearchReponseDto> searchFriendsMap = user.map(u -> new FriendSearchReponseDto(u.getUserImageUrl(), u.getUserName()));
        List<FriendSearchReponseDto> content = searchFriendsMap.getContent();
        long totalCount = searchFriendsMap.getTotalElements();

        //리스트 반환(UserDto)로 반환 -> 제너럴 사용하여
        FriendSearchResultDto result = new FriendSearchResultDto(0, totalCount, content);

        return result;
    }
}
