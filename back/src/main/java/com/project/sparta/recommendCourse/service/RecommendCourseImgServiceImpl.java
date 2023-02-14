package com.project.sparta.recommendCourse.service;

import com.project.sparta.recommendCourse.entity.RecommendCourseBoard;
import com.project.sparta.recommendCourse.entity.RecommendCourseImg;
import com.project.sparta.recommendCourse.repository.RecommendCourseBoardImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecommendCourseImgServiceImpl implements RecommendCourseImgService {

    private final RecommendCourseBoardImgRepository recommendCourseBoardImgRepository;

    //이미지 리스트 변환
    @Override
    public void createImgList(List<MultipartFile> multipartFiles) throws IOException {
        List<RecommendCourseImg> imgList = new ArrayList<>();
        //전달된 파일이 존재할경우
        if (!multipartFiles.isEmpty()) {

            //업로드 날짜 저장
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMdd");

            // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
//        String imgRoute = new File("").getAbsolutePath() + File.separator;

            //파일 세부경로 지정
            String path = System.getProperty("user.dir") + "/src/main/resources/static/files";
            File file = new File(path);

            // 저장할 위치의 디렉토리가 존재하지 않을 경우
            if (!file.exists()) {
                // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
                file.mkdirs();
            }

            for (MultipartFile multipartFile : multipartFiles) {
                //
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                // 확장자명이 존재하지 않을 경우 처리 x
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    } else {
                        // 다른 확장자일 경우 처리 x
                        break;
                    }

                }

                //UUID로 랜덤값을 지정해줘도 되지만 나노타임으로 사용했다.
                String newImgName = System.nanoTime() + originalFileExtension;
                RecommendCourseImg courseImg = RecommendCourseImg.builder()
                        .imgSize(multipartFile.getSize())
                        .imgRoute(path + File.separator + newImgName) // File.separator는 슬래시 방언 통합해주는 것이다.
                        .originalImgName(multipartFile.getOriginalFilename().toString())
                        .build();

                //이미지 리스트에 추가
                imgList.add(courseImg);

                //디비에 저장
                recommendCourseBoardImgRepository.save(courseImg);

                // 업로드 한 파일 데이터를 지정한 파일에 저장
                file = new File(path + File.separator + newImgName);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);
            }

        }
//        return imgList;

    }

    //이미지 파일 삭제

    @Override
    public void deleteImgList(Long id){
        //포스트 아이디값으로 들어있는 이미지들을 찾아서
        List<Long> byRecommendCoursePostId = recommendCourseBoardImgRepository.findByRecommendCoursePostId(id);
        //이미지 리스트에서 이미지들의 아이디 리스트를 뽑아서
        //그 아이디들을 삭제한다. (한방쿼리 쓰면 좋을 것 같은데)
        for (Long imgId:byRecommendCoursePostId) {
            recommendCourseBoardImgRepository.deleteById(imgId);
        }
    }

    @Override
    public void saveimage(RecommendCourseImg recommendCourseImg){
        recommendCourseBoardImgRepository.save(recommendCourseImg);
    }

}
