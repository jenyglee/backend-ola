package com.project.sparta.alarm.controller;

import com.project.sparta.alarm.dto.AlarmRequetDto;
import com.project.sparta.alarm.dto.AlarmResponseDto;
import com.project.sparta.alarm.service.AlarmService;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.security.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"알람"})
@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    //알람 내용 저장
    @ApiOperation(value = "알림 생성", response = Join.class)
    @PostMapping("/alarm")
    public void createAlarm(@RequestBody AlarmRequetDto alarmRequetDto, @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        alarmService.createAlarm(alarmRequetDto, userDetails.getUser().getNickName());
    }

    //알람 내용 조회
    @ApiOperation(value = "나의 알림 전체 조회", response = Join.class)
    @GetMapping("/alarm")
    public ResponseEntity getMyAlarmList(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size){
        PageResponseDto<List<AlarmResponseDto>> alarmResponseDto = alarmService.getMyAlarmList(userDetails.getUser(), page, size);
        return new ResponseEntity(alarmResponseDto, HttpStatus.OK);
    }

    //알람 읽으면 AlarmStatus가 수정됨
    @ApiOperation(value = "나의 알림 상태값 수정", response = Join.class)
    @PatchMapping("/alarm")
    public void updateAlarmStatus(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam Long alarmId){
        alarmService.updateAlarmStatus(userDetails.getUser().getId(),alarmId);
    }

    //알람 삭제
    @ApiOperation(value = "나의 알림 삭제", response = Join.class)
    @DeleteMapping("/alarm")
    public void deleteAlarm(@ApiIgnore @RequestParam Long boardId){
        alarmService.deleteAlarm(boardId);
    }

    //알람 삭제 전 boardId에 딸린 알림 내용 조회
    @ApiOperation(value = "알림 조회", response = Join.class)
    @GetMapping("/alarms")
    public ResponseEntity getAlarmList(@ApiIgnore @RequestParam Long boardId){
        List<AlarmResponseDto> alarmList = alarmService.getAlarmList(boardId);
        return new ResponseEntity(alarmList, HttpStatus.OK);
    }
}
