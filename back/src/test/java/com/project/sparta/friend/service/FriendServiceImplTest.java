package com.project.sparta.friend.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FriendServiceImplTest {

    // @Autowired
    // EntityManager em;
    // JPAQueryFactory queryFactory;
    // @BeforeEach
    // public void before() {
    //     queryFactory = new JPAQueryFactory(em);
    // }
    //
    // @Autowired
    // FriendService friendService;
    //

    //
    // @Test
    // public void addFriend(){
    //     friendService.addFriend(1L, "한세인");
    //     friendService.addFriend(1L, "김민선");
    //     List<Friend> friend= friendRepository.findByUserId(1L);
    //
    //     Assertions.assertThat(friend.get(0).getTargetId()).isEqualTo(2L);
    //     Assertions.assertThat(friend.get(1).getTargetId()).isEqualTo(3L);
    // }
    //
    //
    // @Test
    // public void deleteFriend(){
    //     friendService.addFriend(1L, "한세인");
    //     friendService.addFriend(1L, "김민선");
    //
    //     friendService.deleteFriend(2L);
    //     List<Friend> friend = friendRepository.findByUserId(1L);
    //
    //     Assertions.assertThat(friend.size()).isEqualTo(1);
    // }
    //
    // @Test
    // public void searchFriend(){
    //     friendService.addFriend(1L, "한세인");
    //     friendService.addFriend(1L, "김민선");
    //
    //     PageResponseDto result = friendService.searchFriend(0, 5, "한");
    //     Assertions.assertThat(result.getCurrentPage()).isEqualTo(0);
    //     Assertions.assertThat(result.getTotalCount()).isEqualTo(5);
    // }
    //
    // @Test
    // void allMyFriendList() {
    //     //내 친구 목록 전체 조회
    //     friendService.addFriend(1L, "한세인");
    //     friendService.addFriend(1L, "김민선");
    //     PageResponseDto result = friendService.AllMyFriendList(0, 5, 1L);
    //     Assertions.assertThat(result.getTotalCount()).isEqualTo(2);
    // }
    //
    // @Test
    // void allRecomentFriendList() {
    //     PageResponseDto<List<FriendInfoReponseDto>> result = friendService.AllRecomentFriendList(0, 5, 1L);
    //     Assertions.assertThat(result.getTotalCount()).isEqualTo(4);
    // }
}