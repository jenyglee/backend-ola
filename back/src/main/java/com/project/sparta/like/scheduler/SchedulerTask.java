package com.project.sparta.like.scheduler;

import com.project.sparta.like.repository.LikeCommentRepositoryImpl;
import com.project.sparta.like.service.CommentLikeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerTask {

  private final LikeCommentRepositoryImpl likeCommentRepository;

  private final CommentLikeService commentLikeService;
  @Scheduled(cron = "0/5 * * * * *")
  public void commentLikeBulkCnt() {

    if (commentLikeService.start_schedule()) {
      likeCommentRepository.bulkUpdateLike();
    }
  }

}