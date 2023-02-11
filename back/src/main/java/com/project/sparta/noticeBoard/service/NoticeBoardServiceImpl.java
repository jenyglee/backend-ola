package com.project.sparta.noticeBoard.service;

import com.project.sparta.admin.entity.Admin;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.dto.NoticeBoardResponseDto;
import com.project.sparta.noticeBoard.entity.NoticeBoard;
import com.project.sparta.noticeBoard.repository.NoticeBoardRepository;
import com.project.sparta.user.entity.User;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeBoardServiceImpl implements NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;

    @Override
    public NoticeBoard createNoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto, Admin admin) {
        NoticeBoard noticeBoard = new NoticeBoard(noticeBoardRequestDto);
        noticeBoardRepository.save(noticeBoard);
        return noticeBoard;
    }

    @Override
    public void deleteNoticeBoard(Long id, Admin admin) {
       NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(
               () -> new IllegalArgumentException("공지사항을 찾을 수 없습니다")
       );
       noticeBoardRepository.delete(noticeBoard);




    }

    @Override
    public NoticeBoardResponseDto updateNoticeBoard(NoticeBoardRequestDto noticeBoardRequestDto, Long boardId, Admin admin) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(boardId).orElseThrow(
                ()-> new IllegalArgumentException("공지사항을 찾을 수 없습니다")
        );
        noticeBoard.update(noticeBoardRequestDto);

        return new NoticeBoardResponseDto(noticeBoard);


    }

    @Override
    public NoticeBoardResponseDto getNoticeBoard(Long id, User user) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("공지사항을 찾을 수 없습니다")
        );
       return new NoticeBoardResponseDto(noticeBoard);

    }



    @Override
    public PageResponseDto<List<NoticeBoardResponseDto>> getAllNoticeBoard(int offset, int limit, User user) {
        // 1. 페이징으로 요청해서 조회
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<NoticeBoard> results = noticeBoardRepository.findAll(pageRequest);

        // 2. 데이터, 전체 개수 추출
        List<NoticeBoard> noticeBoardList = results.getContent();
        long totalElements = results.getTotalElements();

        // 3. 엔티티를 DTO로 변환
        List<NoticeBoardResponseDto> noticeBoardResponseDtoList = new ArrayList<>();
        noticeBoardResponseDtoList = noticeBoardList
            .stream()
            .map(NoticeBoardResponseDto::new)
            .collect(Collectors.toList());


        //4. 클라이언트에 응답(현재페이지, 전체 건수, 데이터 포함)
        return new PageResponseDto<>(offset, totalElements, noticeBoardResponseDtoList);
    }
}
