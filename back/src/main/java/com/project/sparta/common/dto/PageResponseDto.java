package com.project.sparta.common.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class PageResponseDto<T> {
    private int currentPage;
    private long totalCount;
    private T data;

    public PageResponseDto(int currentPage, long totalCount, T data) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.data = data;
    }
}
