
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
        public void userInit() {
            // 유저 10명 생성
            for (int i = 0; i < 3; i++) {
                userService.signup(
                        UserSignupDto.builder()
                                .email("user" + i + "@naver.com")
                                .password("1234")
                                .nickName("열즈엉열즈엉" + i)
                                .age(i)
                                .phoneNumber("01012345678")
                                .imageUrl(
                                        "https://blog.kakaocdn.net/dn/dni4U6/btq2noFTWD4/mZkwEf6bFDIVS9WZqnxjTK/img.png")
                                .tagList(Arrays.asList(1L, 2L, 3L))
                                .build()
                );
            }
            for (int i = 3; i < 6; i++) {
                userService.signup(
                        UserSignupDto.builder()
                                .email("user" + i + "@naver.com")
                                .password("1234")
                                .nickName("등산꼰대" + i)
                                .age(i)
                                .phoneNumber("01012345678")
                                .imageUrl(
                                        "https://pbs.twimg.com/media/FJd_2ikaQAIYi3M.jpg")
                                .tagList(Arrays.asList(4L, 5L, 6L))
                                .build()
                );
            }
            for (int i = 6; i < 10; i++) {
                userService.signup(
                        UserSignupDto.builder()
                                .email("user" + i + "@naver.com")
                                .password("1234")
                                .nickName("등산마초" + i)
                                .age(i)
                                .phoneNumber("01012345678")
                                .imageUrl(
                                        "https://img.seoul.co.kr/img/upload/2022/07/26/SSI_20220726154956_O2.jpg")
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
        public void recommendInit() {
            for (int i = 0; i < 5; i++) {
                List<String> imgList = new ArrayList<>();

                imgList.add("https://post-phinf.pstatic.net/MjAyMjExMTRfMTAy/MDAxNjY4NDEwNDg0NTkz.hUhqseEkRWWDOYH39iEysl9jDZ0IyjOGx2gxc25wtGIg.BY9N8plvaiNKPsEaisWfVuNtSDU6p8J7_IscgwGhAWcg.JPEG/20201225062642116.jpg?type=w1200");
                imgList.add("https://post-phinf.pstatic.net/MjAyMjExMTVfMzAw/MDAxNjY4NDc0NzQyNjQ3.rPp-yqJsgfQ1SLBzykTxy20QrsWHyfDD_2K4yUnHTh0g.KZIlUrdyDNBPGBWCxJ2zE9QiNDIOBvZMeQk6IcesQ4sg.JPEG/20211114125551444.jpg?type=w1200");


                String thumbnail = "https://www.50plus.or.kr/upload/im/2017/11/3b8e4193-bbec-4058-928d-1934d8a8e01e.jpg";

                recommendCourseBoardService.creatRecommendCourseBoard(
                        RecommendRequestDto.builder()
                                .score(1)
                                .title("설경이 아름다운 겨울산 추천" + i)
                                .season("겨울")
                                .altitude((int) (Math.random() * 100) + 100)
                                .contents("처음부터 설악산이 나와 놀라셨나요?\n" +
                                        "\n" +
                                        "설악산은 초심자부터 경험자까지\n" +
                                        "모두 겨울 산행을 즐기기 좋은 산이에요!" + i)
                                .region("강원도 양양군 서면 오색리 #설악산")
                                .imgList(imgList)
                                .thumbnail(thumbnail)
                                .build()
                        , 10L
                );
            }
            for (int i = 5; i < 10; i++) {
                List<String> imgList = new ArrayList<>();
                imgList.add("https://post-phinf.pstatic.net/MjAxOTEwMTBfMjcg/MDAxNTcwNjg5NDg0NDg4.zRnWItLpMcVHeDGrBBtpugR5TZZ_m-PCzKF19ohXtuAg.2UtUY1lXS0XBhHKJISOhEWZQUeMDWLgPbk-OlsBNWp0g.JPEG/%EC%95%84%EC%B0%A8%EC%82%B0_.jpg?type=w1200");
                imgList.add("https://post-phinf.pstatic.net/MjAxOTEwMTBfMjcy/MDAxNTcwNjg5NTc3NzQy.KUosD9Z66FF5sHHxtmbE34jHOdT5JmkGetCoeSp0z88g.30uEuNuOKxA-InFTTloCPvdxOi2fyZTjD7sT_CMDbaYg.JPEG/1989966.jpg?type=w1200");
                String thumbnail = "https://cdn.mhns.co.kr/news/photo/202011/420732_549374_3013.jpg";

                recommendCourseBoardService.creatRecommendCourseBoard(
                        RecommendRequestDto.builder()
                                .score(2)
                                .title("단풍은 지금 최고 절정! 꼭 가봐야 할 가을산 " + i)
                                .season("가을")
                                .altitude((int) (Math.random() * 100) + 200)
                                .contents("푸른색의 잎이 붉게 변하는 가을이 돌아왔어요.\n" +
                                        "아이들에게 오색단풍이 아름다운 가을을 마음껏 느끼게 해주고 싶다면 가을산은 어떠세요?\n" +
                                        " 어떤 산이 있는지 함께 만나볼까요?" + i)
                                .region("서울특별시 서초구 #청계산")
                                .imgList(imgList)
                                .thumbnail(thumbnail)
                                .build()
                        , 9L
                );
            }
            for (int i = 10; i < 15; i++) {
                List<String> imgList = new ArrayList<>();

                imgList.add("https://cdn.imweb.me/upload/S2021011502a2f4eeeb339/49bef1f0d3ca7.jpeg");
                imgList.add("https://cdn.imweb.me/upload/S2021011502a2f4eeeb339/75ed9c2fa54ef.png");
                String thumbnail = "http://db.kookje.co.kr/news2000/photo/2022/0203/L20220203.22012000210i1.jpg";

                recommendCourseBoardService.creatRecommendCourseBoard(
                        RecommendRequestDto.builder()
                                .score(3)
                                .title("올 봄 여기 꼭 가야지! 봄꽃 산행 BEST " + i)
                                .season("봄")
                                .altitude((int) (Math.random() * 100) + 300)
                                .contents("벚꽃 러버들은 다 모이세요~! 세상에 이렇게 아름다운 벚꽃 길이 무려 산 위에 있어요!\n" +
                                        "\n" +
                                        "장복산의 특징은 바짝 오르막을 올라가면 \"꽃 길만 걸으세요~\"에 딱 어울리는 벚꽃터널이 나옵니다.\n" +
                                        "\n" +
                                        "벚꽃터널을 산 위에서 볼 수 있을 줄은 상상도 못했는데, 산 능선에 펼쳐지는 벚꽃 터널은 감동 그 자체에요 :)\n" +
                                        "\n" +
                                        "게다가 벚꽃 너머 보이는 아름다운 바다까지 한 데 어우러져 그야말로 감동의 뷰를 자아내요." + i)
                                .region("경상도 창원시 진해구")
                                .imgList(imgList)
                                .thumbnail(thumbnail)
                                .build()
                        , 8L
                );
            }
            for (int i = 15; i < 20; i++) {
                List<String> imgList = new ArrayList<>();

                imgList.add("https://cdn.san.chosun.com/news/photo/202207/21486_79137_2732.jpg");
                imgList.add("https://cdn.san.chosun.com/news/photo/202207/21486_79138_2733.jpg");

                String thumbnail = "https://t1.daumcdn.net/cfile/tistory/1214144F4FD9948407";

                recommendCourseBoardService.creatRecommendCourseBoard(
                        RecommendRequestDto.builder()
                                .score(4)
                                .title("8월에 갈 만한 산 BEST" + i)
                                .season("여름")
                                .altitude((int) (Math.random() * 100) + 400)
                                .contents("무의도에는 영화나 드라마 촬영장으로 유명한 실미해수욕장,\n" +
                                        "하나개해수욕장 같은 풍광이 좋은 해수욕장이 있다. 여름철에 해변의 캠핑장에서 야영하며 피서를 겸한 산행을 즐길 수 있다.\n" +
                                        " 영종도와 인근 섬들 가운데 가장 먼저 산꾼들이 찾기 시작한 곳이 바로 무의도다." + i)
                                .region("전라도 영광군 대마면 남산리")
                                .imgList(imgList)
                                .thumbnail(thumbnail)
                                .build()
                        , 7L
                );
            }
        }

        @Transactional
        public void communityInit() {
            User user1 = em.find(User.class, 1L);
            User user2 = em.find(User.class, 2L);
            User user3 = em.find(User.class, 3L);
            User user4 = em.find(User.class, 4L);
            List<String> imgList = new ArrayList<>();

            imgList.add("https://www.3hoursahead.com/_next/image?url=https%3A%2F%2Fcdn.3hoursahead.com%2Fv2%2Fcontent%2Fimage-comp%2Fcef21457-a6cd-45dc-8bbc-bbb3abc1c222.webp&w=1080&q=75");
            imgList.add("https://www.3hoursahead.com/_next/image?url=https%3A%2F%2Fcdn.3hoursahead.com%2Fv2%2Fcontent%2Fimage-comp%2Fe11c67de-56a6-4e58-aafc-aaa16e666a47.webp&w=1080&q=75");

            for (int i = 0; i < 5; i++) {
                communityBoardService.createCommunityBoard(
                        CommunityBoardRequestDto.builder()
                                .title("겨울왕국이 된 겨울 한라산 정복" + i)
                                .contents("NS에 겨울 왕국이 된 한라산 백록담을 보고 마음을 뺏겼다면 주목!!!! \n" +
                                        "눈 온 한라산을 가보고는 싶지만 등산과 친하지 않아 걱정이라면 이 매거진을 정독하면 될 일이다. \n" +
                                        "백록담으로 향하는 전체적인 코스 설명부터 겨울 등산 준비물과 주의할 사항까지 꾹꾹 담았다. \n" + i)
                                .chatStatus("N")
                                .chatMemCnt(0)
                                .tagList(Arrays.asList(7L, 8L, 9L))
                                .imgList(imgList)
                                .build(),
                        user1
                );
            }
            for (int i = 5; i < 10; i++) {
                communityBoardService.createCommunityBoard(
                        CommunityBoardRequestDto.builder()
                                .title("겨울왕국이 된 겨울 한라산 정복" + i)
                                .contents("NS에 겨울 왕국이 된 한라산 백록담을 보고 마음을 뺏겼다면 주목!!!! \n" +
                                        "눈 온 한라산을 가보고는 싶지만 등산과 친하지 않아 걱정이라면 이 매거진을 정독하면 될 일이다. \n" +
                                        "백록담으로 향하는 전체적인 코스 설명부터 겨울 등산 준비물과 주의할 사항까지 꾹꾹 담았다. \n" + i)
                                .chatStatus("N")
                                .chatMemCnt(0)
                                .tagList(Arrays.asList(7L, 8L, 9L))
                                .imgList(imgList)
                                .build(),
                        user2
                );
            }
            for (int i = 10; i < 15; i++) {
                communityBoardService.createCommunityBoard(
                        CommunityBoardRequestDto.builder()
                                .title("초보자가 꼭 가봐야 할 서울 등산코스!" + i)
                                .contents("왕복 1시간 정도 소요됐던 입문자 등산코스에서  \n" +
                                        "\n" +
                                        "한단계 업그레이드된 초보자 등산코스는  \n" +
                                        "\n" +
                                        "고도 약 300m대의 산으로,\n" +
                                        "\n" +
                                        "왕복 2시간 정도 소요되는 등산코스랍니다! " + i)
                                .chatStatus("N")
                                .chatMemCnt(0)
                                .tagList(Arrays.asList(7L, 8L, 9L))
                                .imgList(imgList)
                                .build(),
                        user3
                );
            }
            for (int i = 15; i < 20; i++) {
                communityBoardService.createCommunityBoard(
                        CommunityBoardRequestDto.builder()
                                .title("걷고 싶은 한국의 아름다운 산길 10곳" + i)
                                .contents("국토의 70%가 산인 한국은 산책하듯 즐길 수 있는 하이킹 코스부터 운동을 좋아하는 상급자용 코스까지\n" +
                                        " 다양한 하이킹 코스를 만날 수 있는 곳이에요. 하이킹 초보자라도 걱정하지 마세요" + i)
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
        public void communityCommentInit() {
            User user = em.find(User.class, 2L);
            for (int i = 0; i < 22; i++) {
                CommunityRequestDto requestDto = new CommunityRequestDto("너무너무가고싶당!" + i);
                communityCommentService.createCommunityComments(1L, requestDto, user);
            }
        }

        @Transactional
        public void communityLike() {
            User user1 = em.find(User.class, 1L);
            User user2 = em.find(User.class, 2L);
            User user3 = em.find(User.class, 3L);
            User user4 = em.find(User.class, 4L);
            User user5 = em.find(User.class, 5L);
            User user6 = em.find(User.class, 6L);
            User user7 = em.find(User.class, 7L);
            User user8 = em.find(User.class, 8L);

            boardLikeService.likeBoard(1L, user1);
            boardLikeService.likeBoard(1L, user2);
            boardLikeService.likeBoard(1L, user3);
            boardLikeService.likeBoard(1L, user4);
            boardLikeService.likeBoard(1L, user5);
            commentLikeService.likeComment(1L, user1);
            commentLikeService.likeComment(1L, user2);
            commentLikeService.likeComment(1L, user3);
            commentLikeService.likeComment(1L, user4);
            commentLikeService.likeComment(2L, user5);
            commentLikeService.likeComment(2L, user6);
            commentLikeService.likeComment(2L, user7);
            commentLikeService.likeComment(2L, user8);
        }

        @Transactional
        public void noticeInit() {
            User admin = em.find(User.class, 11L);
            for (int i = 0; i < 12; i++) {
                noticeBoardService.createNoticeBoard(
                        NoticeBoardRequestDto.builder()
                                .title("서비스공지" + i)
                                .contents("등급관련 권한사항")
                                .category(NoticeCategoryEnum.SERVICE)
                                .build(),
                        admin
                );
            }
            for (int i = 0; i < 12; i++) {
                noticeBoardService.createNoticeBoard(
                        NoticeBoardRequestDto.builder()
                                .title("이벤트공지" + i)
                                .contents("등산 사진 콘테스트")
                                .category(NoticeCategoryEnum.EVENT)
                                .build(),
                        admin
                );
            }
            for (int i = 0; i < 12; i++) {
                noticeBoardService.createNoticeBoard(
                        NoticeBoardRequestDto.builder()
                                .title("업데이트공지" + i)
                                .contents("회원들간의 채팅기능 업데이트!!")
                                .category(NoticeCategoryEnum.UPDATE)
                                .build(),
                        admin
                );
            }
        }
    }
}


