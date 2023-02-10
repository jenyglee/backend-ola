package com.project.sparta.exception;

import com.project.sparta.exception.api.Status;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private Status status;

    public CustomException(Status status) {
        this.status = status;
    }
}
