package com.project.sparta.communityComment.repository;

import com.project.sparta.communityComment.entity.CommunityComment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommunityComment, Long> {

}
