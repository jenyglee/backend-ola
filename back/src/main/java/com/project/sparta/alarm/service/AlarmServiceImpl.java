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
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService{

    private final AlarmRespository alarmRespository;
    private final BoardRepository boardRepository;
    private final RecommendCourseBoardRepository recommendCourseBoardRepository;
    private final UserRepository userRepository;

    @Override
    public void createAlarm(AlarmRequetDto alarmRequetDto, String nickName) {

        if(alarmRequetDto.getBoardType().equals("community")){
           CommunityBoard board = boardRepository.findById(alarmRequetDto.getBoardId()).orElseThrow(()-> new CustomException(
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

        }else if (alarmRequetDto.getBoardType().equals("recommend")){
            RecommendCourseBoard board = recommendCourseBoardRepository.findById(alarmRequetDto.getBoardId()).orElseThrow(()-> new CustomException(Status.NOT_FOUND_POST));

            User user = userRepository.findById(board.getUserId()).orElseThrow(()-> new CustomException(Status.NOT_FOUND_USER));

            Alarm alarm = Alarm.builder()
                .message(alarmRequetDto.getMessage())
                .boardId(board.getId())
                .userId(board.getUserId())
                .userNickName(user.getNickName())
                .readStatus("N")
                .build();
            alarmRespository.saveAndFlush(alarm);
        }
    }
    @Override
    public PageResponseDto<List<AlarmResponseDto>> getMyAlarmList(User user, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Alarm> alarms = alarmRespository.findMyAlarmList(user.getId(), pageRequest, user.getNickName());

        Page<AlarmResponseDto> alarmList = alarms.map(alarm -> new AlarmResponseDto(alarm.getId(), alarm.getMessage(), alarm.getUserNickName(), alarm.getReadStatus(), alarm.getBoardId()));
        List<AlarmResponseDto> content = alarmList.getContent();

        long totalCount = alarmList.getTotalElements();
        return new PageResponseDto<>(page, totalCount, content);
    }

    @Override
    public void updateAlarmStatus(Long userId, Long alarmId) {

        Alarm alarm = alarmRespository.findByIdAndUserId(alarmId, userId).orElseThrow(()-> new CustomException(Status.NOT_FOUND_POST));

        alarm.update(alarm.getId(), alarm.getMessage(), alarm.getUserId(), alarm.getUserNickName(), alarm.getBoardId(), "Y", alarm.getWriterNickName());

        alarmRespository.saveAndFlush(alarm);
    }
}
