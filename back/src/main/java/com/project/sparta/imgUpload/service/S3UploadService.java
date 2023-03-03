package com.project.sparta.imgUpload.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.project.sparta.imgUpload.dto.PreSignedURLResponseDto;
import com.project.sparta.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


@Service
@RequiredArgsConstructor
public class S3UploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value(value = "${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value(value = "${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value(value = "${cloud.aws.region.static}")
    private String region;



    private final AmazonS3 amazonS3;

//    //서버 -> S3 이미지 업로드
//    public List<String> uploadFile(List<MultipartFile> multipartFile) {
//        List<String> fileNameList = new ArrayList();
//
//        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
//        multipartFile.forEach(file -> {
//            String fileName = createFileName(file.getOriginalFilename()); // 이름 난수화
//            String key = "ola/" + fileName; //S3에 올릴 오브젝트 키
////            String key = fileName;
//
//            ObjectMetadata objectMetadata = new ObjectMetadata(); //S3에 저장되는 메타데이터 클래스.
//            objectMetadata.setContentLength(file.getSize()); //연결된 개체의 크기를 바이트 단위로 나타내는 Content-Length HTTP 헤더를 설정합니다.
//            objectMetadata.setContentType(file.getContentType()); //연결된 개체에 저장된 콘텐츠 유형을 나타내는 Content-Type HTTP 헤더를 설정합니다.
//
//            try(InputStream inputStream = file.getInputStream()) {
//                amazonS3.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
//                        .withCannedAcl(CannedAccessControlList.PublicRead));
//
//            } catch(IOException e) {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
//            }
//            fileNameList.add(amazonS3.getUrl(bucket, fileName).toString());
//        });
//
//
//        return fileNameList;
//    }



//    //PresignedURL 조회
//    public String getPresignedURL(String fileName) {
////        Regions clientRegion = Regions.AP_NORTHEAST_2;
//        String bucketName = "sparta-ola";
//        String objectKey = "ola/" + createFileName(fileName); //S3에 올릴 오브젝트 키
//
//        // 미리 지정된 URL이 60분 후에 만료되도록 설정합니다.
//        java.util.Date expiration = new java.util.Date();
//        long expTimeMillis = Instant.now().toEpochMilli();
//        expTimeMillis += 1000 * 60 * 60;
//        expiration.setTime(expTimeMillis);
//
//        try {
////            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
////                    .withRegion(clientRegion)
////                    .withCredentials(new ProfileCredentialsProvider())
////                    .build();
//
//            // 미리 서명된 URL을 생성합니다.
//            GeneratePresignedUrlRequest generatePresignedUrlRequest =
//                    new GeneratePresignedUrlRequest(bucketName, objectKey)
//                            .withMethod(HttpMethod.GET)
//                            .withExpiration(expiration);
//            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
//            String preSignedURL = url.toString();
//
//            System.out.println("Pre-Signed URL: " + url.toString());
//            return preSignedURL;
//
//        } catch (AmazonServiceException e) {
//            // 호출이 성공적으로 전송되었지만 Amazon S3에서 처리할 수 없습니다
//            // 오류 응답을 반환했습니다.
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            // 응답 또는 클라이언트에 대해 Amazon S3에 연결할 수 없습니다
//            // Amazon S3의 응답을 구문 분석할 수 없습니다.
//            e.printStackTrace();
//        }
//        return null;
//    }


        //PresignedURL 조회
    public String getPresignedURL(String fileName) {
//        Regions clientRegion = Regions.AP_NORTHEAST_2;
        S3Presigner preSigner = getPreSigner();
        String bucketName = "sparta-ola";
        String objectKey = fileName; //S3에 올릴 오브젝트 키

        // 미리 지정된 URL이 60분 후에 만료되도록 설정합니다.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);

        try {
            // 미리 서명된 URL을 생성합니다.
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            String preSignedURL = url.toString();


            URL imgurl = amazonS3.getUrl(bucketName, objectKey);
            System.out.println("img URL: " + imgurl.toString());
            return preSignedURL;

        } catch (AmazonServiceException e) {
            // 호출이 성공적으로 전송되었지만 Amazon S3에서 처리할 수 없습니다.
            // 오류 응답을 반환했습니다.
            e.printStackTrace();
        }
        return null;
    }

        //미리 서명된 URL 생성 및 객체 업로드
    public PreSignedURLResponseDto signBucket(String fileName) {
        S3Presigner preSigner = getPreSigner();
        String keyName = "ola/" + createFileName(fileName);
        String bucketName = bucket;


        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .contentType("image/png")
                    .build();

            PutObjectPresignRequest preSignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = preSigner.presignPutObject(preSignRequest);
            String myURL = presignedRequest.url().toString();
            System.out.println("Presigned URL to upload a file to: " +myURL);
            System.out.println("Which HTTP method needs to be used when uploading a file: " + presignedRequest.httpRequest().method());

            // 이 URL을 사용하여 Amazon S3 버킷에 콘텐츠를 업로드합니다.
            URL url = presignedRequest.url();

            // 연결을 만들고 이 연결을 사용하여 미리 지정된 URL을 사용하여 새 개체를 업로드합니다.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","text/plain");
            connection.setRequestMethod("PUT");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("This text was uploaded as an object by using a presigned URL.");
            out.close();

            connection.getResponseCode();
            System.out.println("HTTP response code is " + connection.getResponseCode());
            PreSignedURLResponseDto preSignedURLResponseDto = PreSignedURLResponseDto.builder()
                    .preSignedURL(url.toString())
                    .imageName(keyName)
                    .build();


            return preSignedURLResponseDto;

        } catch (S3Exception | IOException e) {
            e.getStackTrace();
        }
        return null;
    }


    //이름 난수화 함수
    private String createFileName(String fileName) { // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 랜덤으로 돌립니다.
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }


    //파일 형식 검증
    private String getFileExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    // 시크릿키,엑세스키 가져오는 메서드
    public S3Presigner getPreSigner(){
        AwsCredentialsProvider awsCredentialsProvider;
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey,secretKey);
        awsCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        return S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }



}
