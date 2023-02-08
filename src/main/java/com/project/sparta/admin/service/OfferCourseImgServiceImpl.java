package com.project.sparta.admin.service;

import com.project.sparta.admin.entity.OfferCourseImg;
import com.project.sparta.admin.repository.OfferCoursePostImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
public class OfferCourseImgServiceImpl implements OfferCourseImgService {

    //이미지 리스트 변환
    @Override
    public List<OfferCourseImg> createImgList(List<MultipartFile> multipartFiles) throws IOException {
        List<OfferCourseImg> imgList = new ArrayList<>();
        //전달된 파일이 존재하지 않을 경우
        if (multipartFiles.isEmpty()) {
            return imgList;
        }
        //전달된 파일이 존재할경우
        //업로드 날짜 저장
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyMMdd");

        // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
        String imgRoute = new File("").getAbsolutePath() + File.separator;

        //파일 세부경로 지정 -> images/20230209/;
//        String path = "images" + File.separator + dateTimeFormatter;
        String path = System.getProperty("user.dir")+"/src/main/resources/static/files"+File.separator + dateTimeFormatter;
        File file = new File(path);

        // 저장할 위치의 디렉토리가 존지하지 않을 경우
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

                } else {
                    // 다른 확장자일 경우 처리 x
                    break;
                }

            }
            String newImgName = System.nanoTime() + originalFileExtension;
            OfferCourseImg courseImg = OfferCourseImg.builder()
                    .imgSize(multipartFile.getSize())
                    .imgRoute(path + "/" + multipartFile.getName() + newImgName)
                    .originalImgName(multipartFile.getName())
                    .build();

            //이미지 리스트에 추가
            imgList.add(courseImg);

            // 업로드 한 파일 데이터를 지정한 파일에 저장
            file = new File(imgRoute + path + File.separator + newImgName);
            multipartFile.transferTo(file);

            // 파일 권한 설정(쓰기, 읽기)
            file.setWritable(true);
            file.setReadable(true);

        }
        return imgList;
    }


        //이미지리스트에 추가


    //이미지 삭제
    @Override
    public void deleteOfferCourseImg() {

    }

    //이미지 조회


}
