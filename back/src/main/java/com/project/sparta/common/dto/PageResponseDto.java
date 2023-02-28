package com.project.sparta.common.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class PageResponseDto<T> implements Serializable {
    private int currentPage;
    private long totalCount;
    private T data;

    public PageResponseDto(int currentPage, long totalCount, T data) {
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.data = data;
    }
}
