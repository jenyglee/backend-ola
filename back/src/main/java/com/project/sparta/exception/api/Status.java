package com.project.sparta.exception.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

// 500 -> INTERNAL SERVER ERROR : 서버에러
// 400 ->  BAD _ REQUEST : 잘못된 요청 (ex. 파라미터 값을 확인해주세요 )
// 409 ->  CONFLICT : 중복 데이터 (ex. 이미 중복된 값)
// 404 ->  NOT _ FOUND : 잘못된 리소스 접근 (ex. 존재하지 않는 값)
// 401 -> 잘못된 인증 및 인가 정보

@Getter
public enum Status {

    // 400 ->  BAD_REQUEST : 잘못된 요청 (ex. 파라미터 값을 확인해주세요 )
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호를 확인해주세요."),
    INVALID_HASHTAG_NAME(HttpStatus.BAD_REQUEST, "해시태그 이름을 입력해주세요."),
    INVALID_CONTENT(HttpStatus.BAD_REQUEST, "내용을 입력해주세요."),
    INVALID_USER(HttpStatus.BAD_REQUEST, "회원정보를 찾을 수 없습니다."),

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    DISCORD_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 일치하지 않습니다."),
   

    //403 -> Forbidden : 권한에러
    NO_PERMISSIONS_RECOMMENDCOURSE(HttpStatus.FORBIDDEN,"게시글작성 등급권한이 없습니다."),
    NO_PERMISSIONS_POST(HttpStatus.FORBIDDEN,"내가 작성한 게시글이 아닙니다."),

    // 404 ->  NOT_FOUND : 잘못된 리소스 접근 (ex. 존재하지 않는 객체)
    NOT_FOUND_HASHTAG(HttpStatus.NOT_FOUND, "해시태그를 찾을 수 없습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND,"게시글을 찾을 수 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"회원을 찾을 수 없습니다."),
    NOT_FOUND_COMMUNITY_BOARD(HttpStatus.NOT_FOUND,"커뮤니티 게시글을 찾을수 없습니다."),
    NOT_FOUND_COMMUNITY_COMMENT(HttpStatus.NOT_FOUND,"변경할 커뮤니티 댓글을 찾을수 없습니다."),

    ADMIN_PASSWORD_NOT_FOUND(NOT_FOUND, "관리자 암호가 일치하지 않아 등록이 불가합니다."),

    // 409 ->  CONFLICT : 중복 데이터 (ex. 이미 중복된 값)
    CONFLICT_HASHTAG(HttpStatus.CONFLICT, "이미 존재하는 해시태그입니다"),
    CONFLICT_FRIEND(HttpStatus.CONFLICT, "이미 친구 관계입니다."),
    CONFLICT_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다"),
    CONFLICT_NICKNAME(HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다"),
    CONFLICT_LIKE(HttpStatus.CONFLICT, "이미 좋아요를 누른 게시글입니다."),

    // 401 -> 잘못된 인증 및 인가 정보
    INVALID_ADMIN_TOKEN(HttpStatus.BAD_REQUEST, "관리자 비밀번호를 잘못 입력했습니다."),
    INVALID_SERVER(HttpStatus.BAD_REQUEST, "잘못된 REFRESH 토큰입니다.");
    private HttpStatus httpStatus;
    private String message;

    Status(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
