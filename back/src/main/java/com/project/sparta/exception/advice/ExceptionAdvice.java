package com.project.sparta.exception.advice;

import com.project.sparta.exception.CustomException;
import com.project.sparta.exception.api.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler({CustomException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiException CustomException(CustomException e){
        log.error("e = {}", e.getStatus().getMessage());
        return new RestApiException(e.getStatus());
    }
}
