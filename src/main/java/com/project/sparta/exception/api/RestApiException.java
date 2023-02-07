package com.project.sparta.exception.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestApiException {
    private HttpStatus httpStatus;
    private String message;
    public RestApiException(Status status) {
        this.httpStatus = status.getHttpStatus();
        this.message = status.getMessage();
    }
}
