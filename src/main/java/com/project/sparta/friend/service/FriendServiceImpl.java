package com.project.sparta.friend.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.exception.CustomException;
import com.project.sparta.friend.dto.FriendSearchReponseDto;
import com.project.sparta.friend.entity.Friend;
import com.project.sparta.friend.repository.FriendRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;
import static com.project.sparta.exception.api.Status.CONFLICT_FRIEND;
import static com.project.sparta.exception.api.Status.INVALID_USER;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //내 친구목록 조회

    //친구 추가
    @Override
    @Transactional
    public void addFriend(Long userId, String targetName) {

        //요청한 친구 정보 유무 확인
        User targetUser = userRepository.findByNickNameAndStatus(targetName, USER_REGISTERED).orElseThrow(()-> new CustomException(INVALID_USER));

        //이미 친구로 등록되어있는지 확인
        Friend check = friendRepository.findByUserIdAndTargetId(userId, targetUser.getId());

        if(check==null){
            friendRepository.saveAndFlush(new Friend(userId, targetUser.getId()));
        }else{
            throw new CustomException(CONFLICT_FRIEND);
        }
    }

    //친구 삭제
    @Override
    @Transactional
    public void deleteFriend(String targetName) {
        User targetUser = userRepository.findByNickNameAndStatus(targetName, USER_REGISTERED).orElseThrow(()->new CustomException(INVALID_USER));

        if(targetUser.equals(null)){
            throw new CustomException(INVALID_USER);
        }
        friendRepository.deleteById(targetUser.getId());
    }

    //친구 검색
    @Override
    @Transactional(readOnly = true)
    public PageResponseDto<List<FriendSearchReponseDto>> searchFriend(int offset, int limit, String targetUserName) {

        //offset , limit 값 임의로 넣기
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "id"));

        //친구 이름으로 검색(+with paging 처리)
        Page<User> user = friendRepository.serachFriend(targetUserName, pageRequest);

        //검색했을 경우에 프로필 사진, 이름 정보 뽑기
        Page<FriendSearchReponseDto> searchFriendsMap = user.map(u -> new FriendSearchReponseDto(u.getUserImageUrl(), u.getNickName()));
        List<FriendSearchReponseDto> content = searchFriendsMap.getContent();
        long totalCount = searchFriendsMap.getTotalElements();

        //리스트 반환
        return new PageResponseDto(offset, totalCount, content);
    }
}
