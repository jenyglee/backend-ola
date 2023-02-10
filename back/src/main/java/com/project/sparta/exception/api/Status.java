package com.project.sparta.exception.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

// 500 -> INTERNAL SERVER ERROR : 서버에러
// 400 ->  BAD _ REQUEST : 잘못된 요청 (ex. 파라미터 값을 확인해주세요 )
// 409 ->  CONFLICT : 중복 데이터 (ex. 이미 중복된 값)
// 404 ->  NOT _ FOUND : 잘못된 리소스 접근 (ex. 존재하지 않는 값)
// 401 -> 잘못된 인증 및 인가 정보

@Getter
public enum Status {

    // 400 ->  BAD_REQUEST : 잘못된 요청 (ex. 파라미터 값을 확인해주세요 )
    INVALID_HASHTAG_NAME(HttpStatus.BAD_REQUEST, "해시태그 이름을 입력해주세요."),
    INVALID_USER(HttpStatus.BAD_REQUEST, "회원정보를 찾을 수 없습니다."),

    // 404 ->  NOT_FOUND : 잘못된 리소스 접근 (ex. 존재하지 않는 객체)
    NOT_FOUND_HASHTAG(HttpStatus.NOT_FOUND, "해시태그를 찾을 수 없습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND,"게시글을 찾을 수 없습니다."),

    // 409 ->  CONFLICT : 중복 데이터 (ex. 이미 중복된 값)
    CONFLICT_HASHTAG(HttpStatus.CONFLICT, "이미 존재하는 해시태그입니다"),
    CONFLICT_FRIEND(HttpStatus.CONFLICT, "이미 친구 관계입니다."),;


    private HttpStatus httpStatus;
    private String message;

    Status(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
