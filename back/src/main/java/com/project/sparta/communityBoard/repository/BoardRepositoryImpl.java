package com.project.sparta.communityBoard.repository;
import static com.project.sparta.communityBoard.entity.QCommunityBoard.communityBoard;
import static com.project.sparta.communityBoard.entity.QCommunityBoardImg.communityBoardImg;
import static com.project.sparta.communityBoard.entity.QCommunityTag.communityTag;
import static com.project.sparta.user.entity.QUser.user;
import com.project.sparta.communityBoard.dto.CommunityBoardAllResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardOneResponseDto;
import com.project.sparta.communityBoard.dto.CommunitySearchCondition;
import com.project.sparta.communityComment.dto.CommentResponseDto;
import com.project.sparta.communityComment.entity.QCommunityComment;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.like.entity.QBoardLike;
import com.project.sparta.like.entity.QCommentLike;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPQLQueryFactory queryFactory;

    public BoardRepositoryImpl(JPQLQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    private final QBoardLike boardLike = new QBoardLike("boardLike");

    private final QCommunityComment communityComment = new QCommunityComment("comment");

    private final QCommentLike commentLike = new QCommentLike("commLike");

    @Override
    public Long countByUserId(Long userId) {
        return queryFactory.selectFrom(communityBoard)
            .where(
                communityBoard.user.Id.eq(userId)
            )
            .fetchCount();
    }

    @Override
    public CommunityBoardOneResponseDto getBoard(Long boardId, Pageable pageable, String username) {
        // 커뮤니티 조회
        Tuple boardCol = queryFactory.select(
                communityBoard.title,
                communityBoard.user.nickName,
                communityBoard.contents,
                communityBoard.chatStatus,
                communityBoard.chatMemCnt,
                JPAExpressions.select(boardLike.count())
                        .from(boardLike)
                        .where(boardLike.board.id.eq(communityBoard.id)),
                JPAExpressions.select(boardLike.userNickName.count().when(1L).then(true)
                        .otherwise(false))
                        .from(boardLike)
                        .where(
                            communityBoard.id.eq(boardLike.board.id),
                            boardLike.userNickName.eq(username)
                        )
            )
            .from(communityBoard)
            .join(communityBoard.user, user)
            .where(
                communityBoard.id.eq(boardId)
            )
            .distinct()
            .fetchOne();


        String title = boardCol.get(communityBoard.title);
        String nickname = boardCol.get(communityBoard.user.nickName);
        String contents = boardCol.get(communityBoard.contents);
        String chatStatus = boardCol.get(communityBoard.chatStatus);
        int chatMemCnt = boardCol.get(communityBoard.chatMemCnt);
        Long communityLikeCount = boardCol.get(JPAExpressions.select(boardLike.count())
            .from(boardLike)
            .where(boardLike.board.id.eq(communityBoard.id)));
        Boolean isLike = boardCol.get(
            JPAExpressions.select(boardLike.userNickName.count().when(1L).then(true)
                    .otherwise(false))
                .from(boardLike)
                .where(
                    communityBoard.id.eq(boardLike.board.id),
                    boardLike.userNickName.eq(username)
                ));

        // 관련 이미지 조회
        List<String> imgCol = queryFactory.select(communityBoardImg.url)
            .from(communityBoardImg, communityBoard)
            .where(
                communityBoard.id.eq(boardId),
                communityBoard.id.eq(communityBoardImg.communityBoard.id)
            )
            .fetch();

        // 관련 해시태그 조회
        List<Hashtag> tagCol = queryFactory.select(communityTag.hashtag)
            .from(communityTag, communityBoard)
            .where(
                communityBoard.id.eq(boardId),
                communityBoard.id.eq(communityTag.communityBoard.id)
            )
            .fetch();

        // 관련 댓글/좋아요 조회
        List<CommentResponseDto> commentCol = queryFactory.select(
                Projections.constructor(CommentResponseDto.class,
                    communityComment.Id,
                    communityComment.nickName,
                    communityComment.contents,
                    communityComment.createAt,
                    JPAExpressions
                        .select(commentLike.count())
                        .from(commentLike)
                        .where(commentLike.comment.Id.eq(communityComment.Id)),
                    // TODO isLike select문때문에 모든 Like가 사라졌을 때 댓글이 하나도 조회가 안되는 이슈 발생
                    JPAExpressions.select(commentLike.userNickName.count().when(1L).then(true)
                            .otherwise(false))
                        .from(commentLike)
                        .where(
                            communityComment.Id.eq(commentLike.comment.Id),
                            commentLike.userNickName.eq(username)
                        ),
                    user.userImageUrl
                )
            )
            .from(communityBoard, communityComment, commentLike, user)
            .where(
                communityBoard.id.eq(boardId),
                communityComment.communityBoardId.eq(communityBoard.id),
                communityComment.nickName.eq(user.nickName)
            )
            .distinct()
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        CommunityBoardOneResponseDto build = CommunityBoardOneResponseDto.builder()
            .title(title)
            .nickName(nickname)
            .contents(contents)
            .likeCount(communityLikeCount)
            .chatMemCnt(chatMemCnt)
            .chatStatus(chatStatus)
            .isLike(isLike)
            .imgList(imgCol)
            .tagList(tagCol)
            .commentList(commentCol)
            .build();
        return build;
    }

    //커뮤니티 게시글 + 커뮤니티 좋아요 + 페이징
    @Override
    public Page<CommunityBoardAllResponseDto> communityAllList(
        CommunitySearchCondition condition, Pageable pageable) {
        StringPath likeCount = Expressions.stringPath("likeCount");
        StringPath date = Expressions.stringPath("date");
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(condition.getSort(), likeCount, date);

        QueryResults<Tuple> results = queryFactory
            .select(
                communityBoard.id,
                communityBoard.user.nickName,
                communityBoard.title,
                ExpressionUtils.as(JPAExpressions.select(boardLike.board.count()).from(boardLike)
                        .where(boardLike.board.id.eq(communityBoard.id)), "likeCount"),
                communityBoard.createAt.as("date")
            )
            .from(communityBoard)
            .join(communityTag).on(communityTag.communityBoard.id.eq(communityBoard.id))
            .where(
                tileEq(condition.getTitle()),
                contentsEq(condition.getContents()),
                nicknameEq(condition.getNickname()),
                // 게시물에 있는 태그가 검색할 태그와 같은 것을 판별하는 과정
                // (재원) communityBoard.tagList를 기준으로 찾으려니까 엄청난 삽질을 했다. 그냥 게시물에 있는 태그 가져온 뒤 그 태그가 검색할 태그와 같은지 찾으면 끝날 문제였는데.
                hashtagEq(condition.getHashtagId()),
                chatStatusEq(condition.getChatStatus())
            )
            .orderBy(orderSpecifiers)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .distinct()
            .fetchResults();
        List<Tuple> boards = results.getResults();

        // 전체 개수
        long total = results.getTotal();

        List<CommunityBoardAllResponseDto> contents = new ArrayList<>();
        for (Tuple tuple : boards) {
            Long id = tuple.get(communityBoard.id);
            String nickName = tuple.get(communityBoard.user.nickName);
            String title = tuple.get(communityBoard.title);
            Long likeSize = tuple.get(
                ExpressionUtils.as(JPAExpressions.select(boardLike.board.count()).from(boardLike)
                    .where(boardLike.board.id.eq(communityBoard.id)), "likeCount"));
            LocalDateTime createAt = tuple.get(communityBoard.createAt.as("date"));

            List<String> imgList = queryFactory.select(communityBoardImg.url)
                .from(communityBoardImg)
                .where(communityBoardImg.communityBoard.id.eq(id))
                .fetch();

            CommunityBoardAllResponseDto build = CommunityBoardAllResponseDto.builder()
                .boardId(id)
                .nickName(nickName)
                .title(title)
                .communityLikeCnt(likeSize)
                .imgList(imgList)
                .createAt(createAt)
                .build();
            contents.add(build);
        }

        return new PageImpl<>(contents, pageable, total);
    }
    @Override
    public Page<CommunityBoardAllResponseDto> communityMyList(Pageable pageable, Long userId) {
        List<CommunityBoardAllResponseDto> boards = queryFactory
            .select(Projections.constructor(CommunityBoardAllResponseDto.class,
                    communityBoard.id,
                    communityBoard.user.nickName,
                    communityBoard.title,
                    ExpressionUtils.as(
                        JPAExpressions.select(boardLike.board.count()).from(boardLike)
                            .where(boardLike.board.id.eq(communityBoard.id)), "communityLikeCnt"),
                    Projections.list(communityBoardImg.url),
                    communityBoard.createAt
                )
            )
            .from(communityBoard)
            .leftJoin(communityBoardImg).on(communityBoardImg.communityBoard.id.eq(communityBoard.id))
            .where(communityBoard.user.Id.eq(userId), communityBoard.chatStatus.eq("N"))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 전체 개수 추출
        long total = queryFactory
            .select(Projections.constructor(CommunityBoardAllResponseDto.class,
                    communityBoard.id,
                    communityBoard.user.nickName,
                    communityBoard.title,
                    ExpressionUtils.as(
                        JPAExpressions.select(boardLike.board.count()).from(boardLike)
                            .where(boardLike.board.id.eq(communityBoard.id)), "communityLikeCnt"),
                    Projections.list(communityBoardImg.url),
                    communityBoard.createAt
                )
            )
            .from(communityBoard)
            .leftJoin(communityBoardImg).on(communityBoardImg.communityBoard.id.eq(communityBoard.id))
            .where(communityBoard.user.Id.eq(userId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchCount();
        return new PageImpl<>(boards, pageable, total);
    }

    // 내가 만든 채팅방 list 전체조회
    @Override
    public Page<CommunityBoardAllResponseDto> myChatBoardList(Long userId, PageRequest pageable) {

        QueryResults<Tuple> results = queryFactory
            .select(
                communityBoard.id,
                communityBoard.user.nickName,
                communityBoard.title,
                ExpressionUtils.as(JPAExpressions.select(boardLike.board.count()).from(boardLike)
                    .where(boardLike.board.id.eq(communityBoard.id)), "communityLikeCnt"),
                communityBoard.createAt
            )
            .from(communityBoard)
            .where(
              communityBoard.user.Id.eq(userId),
              communityBoard.chatStatus.eq("Y")
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();


        List<Tuple> boards = results.getResults();
        long total = results.getTotal();

        List<CommunityBoardAllResponseDto> contents = new ArrayList<>();
        for (Tuple tuple : boards) {
            Long id = tuple.get(communityBoard.id);
            String nickName = tuple.get(communityBoard.user.nickName);
            String title = tuple.get(communityBoard.title);
            Long likeCount = tuple.get(
                ExpressionUtils.as(JPAExpressions.select(boardLike.board.count()).from(boardLike)
                    .where(boardLike.board.id.eq(communityBoard.id)), "communityLikeCnt"));
            LocalDateTime createAt = tuple.get(communityBoard.createAt);

            List<String> imgList = queryFactory.select(communityBoardImg.url)
                .from(communityBoardImg)
                .where(communityBoardImg.communityBoard.id.eq(id))
                .fetch();

            CommunityBoardAllResponseDto build = CommunityBoardAllResponseDto.builder()
                .boardId(id)
                .nickName(nickName)
                .title(title)
                .communityLikeCnt(likeCount)
                .imgList(imgList)
                .createAt(createAt)
                .build();
            contents.add(build);
        }

        return new PageImpl<>(contents, pageable, total);
    }

    private Predicate tileEq(String title) {
        return title != "" ? communityBoard.title.contains(title) : null;
    }
    private Predicate contentsEq(String contents) {
        return contents != "" ? communityBoard.contents.contains(contents) : null;
    }
    private Predicate nicknameEq(String nickname) {
        return nickname != "" ? communityBoard.user.nickName.contains(nickname) : null;
    }
    private Predicate hashtagEq(Long hashtagId) {
        return hashtagId != 0 ? communityTag.hashtag.id.eq(hashtagId) : null;
    }
    private Predicate chatStatusEq(String chatStatus) {
        return chatStatus != "" ? communityBoard.chatStatus.eq(chatStatus) : null;
    }

    // 동적 정렬
    private OrderSpecifier[] createOrderSpecifier(String sort, StringPath likeCount, StringPath date){
        List<OrderSpecifier> orders = new ArrayList<>();
        if(sort.equals("likeDesc")){
            orders.add(new OrderSpecifier(Order.DESC, likeCount));
        }else if(sort.equals("dateDesc")){
            orders.add(new OrderSpecifier(Order.DESC, date));
        }
        return orders.toArray(new OrderSpecifier[orders.size()]);
    }
}
