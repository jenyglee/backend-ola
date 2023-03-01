package com.project.sparta.like.repository;

import static com.project.sparta.communityBoard.entity.QCommunityBoard.communityBoard;
import static com.project.sparta.user.entity.QUser.user;

import com.project.sparta.communityComment.dto.CommentResponseDto;
import com.project.sparta.communityComment.entity.QCommunityComment;
import com.project.sparta.like.entity.QBoardLike;
import com.project.sparta.like.entity.QCommentLike;
import com.project.sparta.like.entity.QCountLike;
import com.project.sparta.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
@Repository
public class LikeCommentRepositoryImpl implements LikeCommentCustomRepository {

  private final JPAQueryFactory queryFactory;

  public LikeCommentRepositoryImpl(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  private final QBoardLike boardLike = new QBoardLike("boardLike");

  private final QCommunityComment communityComment = new QCommunityComment("comment");

  private final QCommentLike commentLike = new QCommentLike("commLike");

  private final QCountLike qCountLike = new QCountLike("countLike");

  public boolean scheduledOn = false;

  @Qualifier
  EntityManager entityManager;

  @Override
  public Boolean isLike() {
    Boolean commentIsLike = queryFactory
        .select(commentLike.userNickName.count().when(1L).then(true)
            .otherwise(false))
        .from(commentLike)
        .where(communityComment.Id.eq(commentLike.comment.Id))
        .distinct()
        .fetchOne();
    return commentIsLike;
  }

  public Long initCountLike() {
    Long cnt = queryFactory
        .select(commentLike.count())
        .from(commentLike)
        .where(commentLike.comment.Id.eq(communityComment.Id))
        .fetchOne();
    System.out.println(cnt);

    return cnt;
  }

  @Override
  public Long updateCountLike() {

    return null;
  }

  @Override
  public void bulkUpdateLike(){
    queryFactory.update(qCountLike)
        .set(qCountLike.likeCnt, qCountLike.likeCnt.add(1))
//        .join(commentLike, qCountLike)
        .where(
            commentLike.isLike.eq(1L)
        )
        .execute();

    queryFactory.update(qCountLike)
        .set(qCountLike.likeCnt, qCountLike.likeCnt.subtract(1))
//        .join(commentLike, qCountLike.comment)
        .where(
            commentLike.isLike.eq(0L))
        .execute();
    entityManager.flush();
    entityManager.clear();
  }

//  @Override
//  public void bulkUpdateLike(){
//    queryFactory.update(qCountLike)
//        .set(qCountLike.likeCnt, qCountLike.likeCnt.add(JPAExpressions.select(commentLike.isLike)
//                .from(commentLike)
//                .where(commentLike.comment.Id.eq(qCountLike.commnetId)))
//            .execute();
//
//    entityManager.flush();
//    entityManager.clear();
//  }
}




//  @Override
//  public void bulkUpdateLike(){
//    queryFactory.update(qCountLike)
//        .set(qCountLike.likeCnt, qCountLike.likeCnt.add(1))
//        .join(commentLike, qCountLike)
//        .where(
//            commentLike.isLike.eq(1L)
//        )
//        .execute();
//
//    queryFactory.update(qCountLike)
//        .set(qCountLike.likeCnt, qCountLike.likeCnt.subtract(1))
//        .join(commentLike, qCountLike.comment)
//        .where(
//            commentLike.isLike.eq(0L))
//        .execute();
//    entityManager.flush();
//    entityManager.clear();
//  }
//}

//  @Override
//  public void bulkUpdateLike(){
//    queryFactory.update(qCountLike)
//        .set(qCountLike.likeCnt, qCountLike.likeCnt.add(1))
//        //.join(communityComment, commentLike)
//        .where(
//            commentLike.isLike.eq(1L),
//            communityComment.Id.eq(commentLike.comment.Id)
//        )
//
//        .execute();
//
//    queryFactory.update(qCountLike)
//        .set(qCountLike.likeCnt, qCountLike.likeCnt.subtract(1))
//        .where(
////            commentLike.isLike.eq(0L),
//            communityComment.Id.eq(commentLike.comment.Id))
//        .execute();
//    entityManager.flush();
//    entityManager.clear();
//  }
//}


//  @Override
//  public void bulkUpdateLike(){
//    queryFactory.update(qCountLike)
//        .set(qCountLike.likeCnt, qCountLike.likeCnt.add(JPAExpressions.select(qCommentLike.isLike).from(qCommentLike).where(qCommentLike.comment.id.eq(qCountLike.comment.id)))
//            .execute();
//
//    entityManager.flush();
//    entityManager.clear();
//  }
//}