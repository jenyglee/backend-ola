package com.project.sparta.alarm.service;

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

    @Override
    public void createAlarm(AlarmRequetDto alarmRequetDto, String nickName) {

        CommunityBoard board = boardRepository.findById(alarmRequetDto.getBoardId())
            .orElseThrow(() -> new CustomException(
                Status.NOT_FOUND_POST));

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

    @Override
    public PageResponseDto<List<AlarmResponseDto>> getMyAlarmList(User user, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Alarm> alarms = alarmRespository.findMyAlarmList(user.getId(), pageRequest,
            user.getNickName());

        Page<AlarmResponseDto> alarmList = alarms.map(
            alarm -> new AlarmResponseDto(alarm.getId(), alarm.getMessage(),
                alarm.getUserNickName(), alarm.getReadStatus(), alarm.getBoardId()));
        List<AlarmResponseDto> content = alarmList.getContent();

        long totalCount = alarmList.getTotalElements();
        return new PageResponseDto<>(page, totalCount, content);
    }

    @Override
    public void updateAlarmStatus(Long userId, Long alarmId) {

        Alarm alarm = alarmRespository.findByIdAndUserId(alarmId, userId)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));

        alarm.update(alarm.getId(), alarm.getMessage(), alarm.getUserId(), alarm.getUserNickName(),
            alarm.getBoardId(), "Y", alarm.getWriterNickName());

        alarmRespository.saveAndFlush(alarm);
    }

    @Override
    public void deleteAlarm(Long boardId) {
        Alarm alarm = alarmRespository.findByBoardId(boardId)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));
        alarmRespository.deleteByBoardId(alarm.getId());
    }

    @Override
    public List<AlarmResponseDto> getAlarmList(Long boardId) {

        List<Alarm> alarmList = alarmRespository.selectAlaram(boardId);
        List<AlarmResponseDto> resultList = new ArrayList<>();

        for (Alarm a : alarmList) {
            AlarmResponseDto alarmResponseDto = AlarmResponseDto.builder()
                .alarmId(a.getId())
                .message(a.getMessage())
                .userNickName(a.getUserNickName())
                .readStatus(a.getReadStatus())
                .boardId(a.getBoardId())
                .build();

            resultList.add(alarmResponseDto);
        }
        return resultList;
    }
}
