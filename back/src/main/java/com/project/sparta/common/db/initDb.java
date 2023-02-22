
package com.project.sparta.common.db;

import com.project.sparta.admin.dto.AdminSignupDto;
import com.project.sparta.admin.service.AdminService;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.service.CommunityBoardService;
import com.project.sparta.communityComment.dto.CommunityRequestDto;
import com.project.sparta.communityComment.entity.CommunityComment;
import com.project.sparta.communityComment.service.CommunityCommentService;
import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.like.service.BoardLikeService;
import com.project.sparta.like.service.CommentLikeService;
import com.project.sparta.noticeBoard.dto.NoticeBoardRequestDto;
import com.project.sparta.noticeBoard.entity.NoticeCategoryEnum;
import com.project.sparta.noticeBoard.service.NoticeBoardService;
import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardImgRepository;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepositoryImpl;
import com.project.sparta.recommendCourse.service.RecommendCourseBoardService;
import com.project.sparta.user.dto.UpgradeRequestDto;
import com.project.sparta.user.dto.UserSignupDto;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class initDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.hashtagInit();
        initService.userInit();
        initService.recommendInit();
        initService.communityInit();
        initService.communityCommentInit();
        initService.communityLike();
        initService.noticeInit();
    }

    @Component
    static class InitService{
        @PersistenceContext
        EntityManager em;
        @Autowired
        UserService userService;
        @Autowired
        AdminService adminService;
        @Autowired
        RecommendCourseBoardService recommendCourseBoardService;

        @Autowired
        RecommendCourseBoardImgRepository recommendCourseBoardImgRepository;

        @Autowired
        CommunityBoardService communityBoardService;
        @Autowired
        CommunityCommentService communityCommentService;
        @Autowired
        NoticeBoardService noticeBoardService;
        @Autowired
        BoardLikeService boardLikeService;

        @Autowired
        CommentLikeService commentLikeService;

        @Transactional
        public void hashtagInit(){
            em.persist(new Hashtag("주변맛집"));
            em.persist(new Hashtag("등산용품"));
            em.persist(new Hashtag("등산데이트"));
            em.persist(new Hashtag("등산친구"));
            em.persist(new Hashtag("봄산"));
            em.persist(new Hashtag("여름산"));
            em.persist(new Hashtag("가을산"));
            em.persist(new Hashtag("겨울산"));
            em.persist(new Hashtag("안전수칙"));
            em.persist(new Hashtag("산악회"));
            em.persist(new Hashtag("10~20대"));
            em.persist(new Hashtag("30~40대"));
            em.persist(new Hashtag("50~60대"));
            em.persist(new Hashtag("등산꿀팁"));
            em.persist(new Hashtag("산추천"));
            em.persist(new Hashtag("계곡추천"));
            em.persist(new Hashtag("등린이"));
            em.persist(new Hashtag("등산입문자"));
            em.persist(new Hashtag("등산매니아"));
            em.persist(new Hashtag("등산모임"));
            em.persist(new Hashtag("1시간걸리는산"));
            em.persist(new Hashtag("2시간걸리는산"));
            em.persist(new Hashtag("3시간걸리는산"));
        }

        @Transactional
        public void userInit(){
            // 유저 10명 생성
            for (int i=0; i<3; i++){
                userService.signup(
                    UserSignupDto.builder()
                        .email("user" + i + "@naver.com")
                        .password("1234")
                        .nickName("user" + i)
                        .age(i)
                        .phoneNumber("01012345678")
                        .imageUrl(
                            "https://i.pinimg.com/236x/e0/a2/55/e0a255de0ff796435db654b03af8f264.jpg")
                        .tagList(Arrays.asList(1L, 2L, 3L))
                        .build()
                );
            }
            for (int i=3; i<6; i++){
                userService.signup(
                    UserSignupDto.builder()
                        .email("user" + i + "@naver.com")
                        .password("1234")
                        .nickName("user" + i)
                        .age(i)
                        .phoneNumber("01012345678")
                        .imageUrl(
                            "https://i.pinimg.com/236x/e0/a2/55/e0a255de0ff796435db654b03af8f264.jpg")
                        .tagList(Arrays.asList(4L, 5L, 6L))
                        .build()
                );
            }
            for (int i=6; i<10; i++){
                userService.signup(
                    UserSignupDto.builder()
                        .email("user" + i + "@naver.com")
                        .password("1234")
                        .nickName("user" + i)
                        .age(i)
                        .phoneNumber("01012345678")
                        .imageUrl(
                            "https://i.pinimg.com/236x/e0/a2/55/e0a255de0ff796435db654b03af8f264.jpg")
                        .tagList(Arrays.asList(7L, 8L, 9L))
                        .build()
                );
            }

            // 3명 등산매니아로 변경
            UpgradeRequestDto mania = new UpgradeRequestDto("MANIA");
            userService.upgrade(mania, 6L);
            userService.upgrade(mania, 7L);
            userService.upgrade(mania, 8L);


            //산신령 2명 추가
            UpgradeRequestDto god = new UpgradeRequestDto("GOD");
            userService.upgrade(god, 9L);
            userService.upgrade(god, 10L);

            //어드민 1명 추가
            adminService.signup(
                AdminSignupDto.builder()
                    .email("admin@naver.com")
                    .password("1234")
                    .nickName("admin")
                    .phoneNumber("01012345678")
                    .adminToken("AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC")
                    .build()
            );
        }

        @Transactional
        public void recommendInit(){
            for (int i=0; i<5; i++){
                List<String> imgList = new ArrayList<>();
                imgList.add("https://t1.daumcdn.net/news/202302/11/daejonilbo/20230211140734415bxqm.jpg");
                imgList.add("https://img1.daumcdn.net/thumb/R300x0/?fname=https://blog.kakaocdn.net/dn/AZY2s/btrLK0upn3G/Wax6UkfTzKXZ6f2wd5AAXk/img.jpg");

                recommendCourseBoardService.creatRecommendCourseBoard(
                    RecommendRequestDto.builder()
                        .score(1)
                        .title("코스추천" + i)
                        .season("가을")
                        .altitude((int) (Math.random() * 100) + 100)
                        .contents("추천해요!" + i)
                        .region("서울")
                        .imgList(imgList)
                        .build()
                    , 10L
                );
            }
            for (int i=5; i<10; i++){
                List<String> imgList = new ArrayList<>();
                imgList.add("https://t1.daumcdn.net/news/202302/11/daejonilbo/20230211140734415bxqm.jpg");
                imgList.add("https://img1.daumcdn.net/thumb/R300x0/?fname=https://blog.kakaocdn.net/dn/AZY2s/btrLK0upn3G/Wax6UkfTzKXZ6f2wd5AAXk/img.jpg");

                recommendCourseBoardService.creatRecommendCourseBoard(
                    RecommendRequestDto.builder()
                        .score(2)
                        .title("코스추천" + i)
                        .season("여름")
                        .altitude((int) (Math.random() * 100) + 200)
                        .contents("추천해요!" + i)
                        .region("경기도")
                        .imgList(imgList)
                        .build()
                    , 9L
                );
            }
            for (int i=10; i<15; i++){
                List<String> imgList = new ArrayList<>();
                imgList.add("https://t1.daumcdn.net/news/202302/11/daejonilbo/20230211140734415bxqm.jpg");
                imgList.add("https://img1.daumcdn.net/thumb/R300x0/?fname=https://blog.kakaocdn.net/dn/AZY2s/btrLK0upn3G/Wax6UkfTzKXZ6f2wd5AAXk/img.jpg");
                recommendCourseBoardService.creatRecommendCourseBoard(
                    RecommendRequestDto.builder()
                        .score(3)
                        .title("코스추천" + i)
                        .season("봄")
                        .altitude((int) (Math.random() * 100) + 300)
                        .contents("추천해요!" + i)
                        .region("전라도")
                        .imgList(imgList)
                        .build()
                    , 8L
                );
            }
            for (int i=15; i<20; i++){
                List<String> imgList = new ArrayList<>();
                imgList.add("https://t1.daumcdn.net/news/202302/11/daejonilbo/20230211140734415bxqm.jpg");
                imgList.add("https://img1.daumcdn.net/thumb/R300x0/?fname=https://blog.kakaocdn.net/dn/AZY2s/btrLK0upn3G/Wax6UkfTzKXZ6f2wd5AAXk/img.jpg");
                recommendCourseBoardService.creatRecommendCourseBoard(
                    RecommendRequestDto.builder()
                        .score(4)
                        .title("코스추천" + i)
                        .season("겨울")
                        .altitude((int) (Math.random() * 100) + 400)
                        .contents("추천해요!" + i)
                        .region("강원도")
                        .imgList(imgList)
                        .build()
                    , 7L
                );
            }
        }

        @Transactional
        public void communityInit(){
            User user1 = em.find(User.class, 1L);
            User user2 = em.find(User.class, 2L);
            User user3 = em.find(User.class, 3L);
            User user4 = em.find(User.class, 4L);
            List<String> imgList = new ArrayList<>();
            imgList.add("https://t1.daumcdn.net/news/202302/11/daejonilbo/20230211140734415bxqm.jpg");
            imgList.add("https://img1.daumcdn.net/thumb/R300x0/?fname=https://blog.kakaocdn.net/dn/AZY2s/btrLK0upn3G/Wax6UkfTzKXZ6f2wd5AAXk/img.jpg");
            for (int i=0; i<5; i++){
                communityBoardService.createCommunityBoard(
                    CommunityBoardRequestDto.builder()
                        .title("커뮤니티" + i)
                        .contents("커뮤니티 콘텐츠" + i)
                        .chatStatus("N")
                        .chatMemCnt(0)
                        .tagList(Arrays.asList(7L, 8L, 9L))
                        .imgList(imgList)
                        .build(),
                    user1
                );
            }
            for (int i=5; i<10; i++){
                communityBoardService.createCommunityBoard(
                    CommunityBoardRequestDto.builder()
                        .title("커뮤니티" + i)
                        .contents("커뮤니티 콘텐츠" + i)
                        .chatStatus("N")
                        .chatMemCnt(0)
                        .tagList(Arrays.asList(7L, 8L, 9L))
                        .imgList(imgList)
                        .build(),
                    user2
                );
            }
            for (int i=10; i<15; i++){
                communityBoardService.createCommunityBoard(
                    CommunityBoardRequestDto.builder()
                        .title("커뮤니티" + i)
                        .contents("커뮤니티 콘텐츠" + i)
                        .chatStatus("N")
                        .chatMemCnt(0)
                        .tagList(Arrays.asList(7L, 8L, 9L))
                        .imgList(imgList)
                        .build(),
                    user3
                );
            }
            for (int i=15; i<20; i++){
                communityBoardService.createCommunityBoard(
                    CommunityBoardRequestDto.builder()
                        .title("커뮤니티" + i)
                        .contents("커뮤니티 콘텐츠" + i)
                        .chatStatus("N")
                        .chatMemCnt(0)
                        .tagList(Arrays.asList(7L, 8L, 9L))
                        .imgList(imgList)
                        .build(),
                    user4
                );
            }
        }

        @Transactional
        public void communityCommentInit(){
            User user = em.find(User.class, 2L);
            for (int i=0; i<22; i++){
                CommunityRequestDto requestDto = new CommunityRequestDto("너무너무가고싶당!" + i);
                communityCommentService.createCommunityComments(1L, requestDto, user);
            }
        }

        @Transactional
        public void communityLike(){
            User user1 = em.find(User.class, 1L);
            User user2 = em.find(User.class, 2L);
            boardLikeService.likeBoard(1L, user1);
            commentLikeService.likeComment(1L, user1);
            commentLikeService.likeComment(1L, user2);
        }

        @Transactional
        public void noticeInit(){
            User admin = em.find(User.class, 11L);
            for (int i=0; i<12; i++){
                noticeBoardService.createNoticeBoard(
                    NoticeBoardRequestDto.builder()
                        .title("서비스공지" + i)
                        .contents("공지사항 내용")
                        .category(NoticeCategoryEnum.SERVICE)
                        .build(),
                    admin
                );
            }
            for (int i=0; i<12; i++){
                noticeBoardService.createNoticeBoard(
                    NoticeBoardRequestDto.builder()
                        .title("이벤트공지" + i)
                        .contents("공지사항 내용")
                        .category(NoticeCategoryEnum.EVENT)
                        .build(),
                    admin
                );
            }
            for (int i=0; i<12; i++){
                noticeBoardService.createNoticeBoard(
                    NoticeBoardRequestDto.builder()
                        .title("업데이트공지" + i)
                        .contents("공지사항 내용")
                        .category(NoticeCategoryEnum.UPDATE)
                        .build(),
                    admin
                );
            }
        }
    }
}


