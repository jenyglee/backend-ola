package com.project.sparta.alarm.service;

import static com.project.sparta.exception.api.Status.*;

import com.project.sparta.alarm.dto.AlarmRequetDto;
import com.project.sparta.alarm.dto.AlarmResponseDto;
import com.project.sparta.alarm.entity.Alarm;
import com.project.sparta.alarm.repository.AlarmRespository;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;
import com.project.sparta.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmServiceImpl implements AlarmService {
    private final AlarmRespository alarmRespository;
    private final BoardRepository boardRepository;

    // 알람 생성
    @Override
    public void createAlarm(AlarmRequetDto alarmRequetDto, String nickName) {
        CommunityBoard board = boardRepository.findById(alarmRequetDto.getBoardId())
            .orElseThrow(() -> new CustomException(NOT_FOUND_COMMUNITY_BOARD));

        Alarm alarm = Alarm.builder()
            .message(board.getTitle() + alarmRequetDto.getMessage())
            .boardId(board.getId())
            .userId(board.getUser().getId())
            .userNickName(board.getUser().getNickName())
            .readStatus("N")
            .writerNickName(nickName)
            .build();

        alarmRespository.saveAndFlush(alarm);
    }

    // 내 알람 전체 조회
    @Override
    public PageResponseDto<List<AlarmResponseDto>> getMyAlarmList(User user, int page, int size) {
        // 1. nickName을 포함하여 전체 조회
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Alarm> alarms = alarmRespository.findMyAlarmList(user.getId(), pageRequest,
            user.getNickName());

        // 2. 조회된 알람 리스트를 DTO 형식으로 변환하여 추출
        List<AlarmResponseDto> content = alarms.stream()
            .map(alarm -> new AlarmResponseDto(
                alarm.getId(),
                alarm.getMessage(),
                alarm.getUserNickName(),
                alarm.getReadStatus(),
                alarm.getBoardId())
            )
            .collect(Collectors.toList());

        // 3. 전체 알람 개수 추출
        long totalCount = alarms.getTotalElements();
        return new PageResponseDto<>(page, totalCount, content);
    }

    // 알람 읽음표시 변경
    @Override
    public void updateAlarmStatus(Long userId, Long alarmId) {
        Alarm alarm = alarmRespository.findByIdAndUserId(alarmId, userId)
            .orElseThrow(() -> new CustomException(NOT_FOUND_POST));

        // 알람 읽음표시를 "Y"로 변경하여 저장
        alarm.updateReadStatus("Y");
        alarmRespository.saveAndFlush(alarm);
    }
}
