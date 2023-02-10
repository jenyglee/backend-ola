package com.project.sparta.product.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.product.dto.ProductResponseDto;

import java.util.List;

public interface NaverApiService {
    PageResponseDto<List<ProductResponseDto>> searchItems(String query , int limit, int offset, String sort);
}
