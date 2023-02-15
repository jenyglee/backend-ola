package com.project.sparta.admin.service;

import com.project.sparta.admin.dto.AdminRequestSignupDto;
import com.project.sparta.admin.dto.ManagerPersonResponseDto;
import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.communityBoard.dto.CommunityBoardRequestDto;
import com.project.sparta.communityBoard.dto.CommunityBoardResponseDto;
import com.project.sparta.communityBoard.entity.CommunityBoard;
import com.project.sparta.communityBoard.repository.BoardRepository;
import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.Status;

import com.project.sparta.recommendCourse.dto.RecommendRequestDto;
import com.project.sparta.recommendCourse.dto.RecommendResponseDto;
import com.project.sparta.recommendCourse.dto.RecommendDetailResponseDto;
import com.project.sparta.recommendCourse.entity.PostStatusEnum;
import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardRepository;
import com.project.sparta.recommendCourse.service.RecommendCourseImgService;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService{
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final RecommendCourseBoardRepository recommandCoursePostRepository;
    private final RecommendCourseImgService recommendCourseImgService;

    // 어드민 회원가입
// ADMIN_TOKEN
    private final PasswordEncoder encoder;

    // 어드민 회원가입
    public void signup(AdminRequestSignupDto adminRequestSignupDto) {
        if(!adminRequestSignupDto.getAdminToken().equals(ADMIN_TOKEN)){
            throw new CustomException(Status.INVALID_ADMIN_TOKEN);
        }
        User admin = User.adminBuilder()
            .email(adminRequestSignupDto.getEmail())
            .password(encoder.encode(adminRequestSignupDto.getPassword()))
            .nickName(adminRequestSignupDto.getNickName())
            .build();
        userRepository.save(admin);
    }

    @Override
    @Transactional
    public CommunityBoardResponseDto updateCommunityBoard(Long community_board_id, CommunityBoardRequestDto communityBoardRequestDto,
        User user) {
        CommunityBoard communityBoard = boardRepository.findById(community_board_id)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
        communityBoard.updateBoard(communityBoardRequestDto);
        boardRepository.saveAndFlush(communityBoard);
        CommunityBoardResponseDto communityBoardResponseDto = new CommunityBoardResponseDto(
            communityBoard);
        return communityBoardResponseDto;
    }

    @Override
    @Transactional
    public void deleteCommunityBoard(Long community_board_id) {
        boardRepository.findById(community_board_id)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
        boardRepository.deleteById(community_board_id);
    }
    @Override
    @Transactional
    public List<CommunityBoardResponseDto> getAllCommunityBoard(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CommunityBoard> boards = boardRepository.findAll(pageable);
        List<CommunityBoardResponseDto> CommunityBoardResponseDtoList = boards.getContent()
            .stream()
            .map(CommunityBoardResponseDto::new)
            .collect(Collectors.toList());
        return CommunityBoardResponseDtoList;
    }
    @Override
    @Transactional
    public CommunityBoardResponseDto getCommunityBoard(Long communityBoardId) {
        CommunityBoard communityBoard = boardRepository.findById(communityBoardId)
            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_COMMUNITY_BOARD));
        CommunityBoardResponseDto communityBoardResponseDto = new CommunityBoardResponseDto(
            communityBoard);
        return communityBoardResponseDto;
    }

//    @Override
//    public List<String> modifyRecommendCoursePost(Long id, List<MultipartFile> images, RecommendRequestDto requestPostDto) throws IOException {
//        // Dto로 수정할 제목이랑 텍스트랑 이미지리스트 받아오고 주소에서 아이디값 받아와서
//        RecommendCourseBoard post = recommandCoursePostRepository.findById(id)
//            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));
////        //수정되기전의 데이터를 삭제해줘야한다. 레파지토리에서 postId값으로 이미지들을 찾아서 리스트로 넘겨준다음에 삭제해준다.
////        recommendCourseImgService.deleteImgList(id);
//
//        // 변경 메서드 하나 만든다음에
//        post.modifyRecommendCourseBoard(requestPostDto.getTitle(), requestPostDto.getContents());
//
////        // 기존에 있던 이미지파일을 디비에서 삭제한다.
////        recommendCourseImgService.deleteImgList(id);
//
//        // 받아온 이미지 파일을 다시 리스트로 변경하고
//        List<RecommendCourseImg> imgList = recommendCourseImgService.createImgList(images);
//
//        //이미지에 포스트 담아주고
//        for (RecommendCourseImg image:imgList) {
//            image.addPost(post);
//        }
//
//        // 스트림으로 이미지 각각의 경로를 뽑아내서
//        List<String> imgRouteList = imgList.stream().map(RecommendCourseImg::getImgRoute).collect(Collectors.toList());
//
//        //포스트 다시 세이브 하면 수정 로직 완료
//        recommandCoursePostRepository.save(post);
//
//        return imgRouteList;
//    }
//
//    /**
//     * 코스추천 삭제 메서드
//     * @param id : 삭제할 게시글 아이디
//     *
//     */
//
//    //코스추천 삭제
//    @Override
//    public void deleteRecommendCoursePost(Long id){
//        //포스트 아이디 조회해서 포스트 아이디 있는지 검증
//        RecommendCourseBoard post = recommandCoursePostRepository.findById(id)
//            .orElseThrow(() -> new CustomException(Status.NOT_FOUND_POST));
//        //포스트의 스테이터스 값 변경하고 다시 저장
//        post.statusModifyRecommendCourse(PostStatusEnum.UNVAILABLE);
//        recommandCoursePostRepository.save(post);
//    }
//
//    //단건 코스 조회
//    @Override
//    public RecommendDetailResponseDto oneSelectRecommendCoursePost(Long id){
//        //1. 아이디값으로 포스트 찾아서 포스트 만들고
//        RecommendCourseBoard recommendCourseBoard = recommandCoursePostRepository.findById(id).orElseThrow();
//
//        //2. 포스트 안에 들어있는 리스트 뽑아서 String<list>로 만들어줌
//        List<String> imgRouteList = recommendCourseBoard.getImages().stream()
//            .map(RecommendCourseImg::getImgRoute).collect(Collectors.toList());
//
//        //3. 리스폰스엔티티에 담아서 클라이언트에게 응답
//        return new RecommendDetailResponseDto(recommendCourseBoard,imgRouteList);
//    }
//
//    //전체 코스 조회(페이징)
//    @Override
//    public PageResponseDto<List<RecommendResponseDto>> allRecommendCoursePost(int offset, int limit){
//        //1.페이징으로 요청해서
//        PageRequest pageRequest = PageRequest.of(offset, limit);
//        Page<RecommendCourseBoard> all = recommandCoursePostRepository.findAll(pageRequest);
//
//        //2.전체 데이터 뽑아서
//        List<RecommendCourseBoard> contentList = all.getContent();
//        long totalElements = all.getTotalElements();
//
//        //3. 엔티티로 만들어서
//        List<RecommendResponseDto> FindAllPostDtoList= new ArrayList<>();
//        for (RecommendCourseBoard coursePost:contentList) {
//            RecommendResponseDto responseFindAllPos = RecommendResponseDto.builder()
//                .title(coursePost.getTitle())
//                .imgList(coursePost
//                    .getImages()
//                    .stream()
//                    .map(RecommendCourseImg::getImgRoute)
//                    .collect(Collectors.toList()))
//                .build();
//            FindAllPostDtoList.add(responseFindAllPos);
//        }
//        // Todo 좋아요 갯수 필드에 추후에 추가
//
//        //4. 클라이언트에 응답.
//        return new PageResponseDto<>(offset,totalElements,FindAllPostDtoList);
//    }
//

    //    @Override
//    public AdminResponseDto getUpdateUser(User user)
//    {
//
//    }
//    @Override
//    public AdminResponseDto getDeleteUser(User user){
//
//    }
    @Override
    public ManagerPersonResponseDto getOneUser(Long id){
        User user = userRepository.findById(id).orElseThrow(()-> new CustomException(Status.INVALID_USER));
        ManagerPersonResponseDto managerPersonResponseDto = new ManagerPersonResponseDto(user);
        return managerPersonResponseDto;
    }
//    @Override
//    public List<AdminResponseDto> getAllUser(){
//
//    }

}
