package com.project.sparta.communityComment.repository;

import com.project.sparta.communityComment.entity.CommunityComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<CommunityComment, Long> {
    @Query("select cc from CommunityComment cc where cc.Id=:commentId and"
        + " cc.nickName=:nickname")
    Optional<CommunityComment> findByIdAndNickName(@Param("commentId") Long commentId, @Param("nickname") String nickname);
    @Query("select cc.Id from CommunityComment cc where cc.communityBoardId=:boardId")
    List<Long> findIdsByCommunityBoardId(@Param("boardId") Long boardId);

    @Query("delete from CommunityComment cc where cc.communityBoardId=:boardId")
    @Modifying
    void deleteCommentAllByInBoardId(@Param("boardId") Long boardId);

//    List<CommunityComment> findAllByCommunityBoardId(Long boardId);
}
