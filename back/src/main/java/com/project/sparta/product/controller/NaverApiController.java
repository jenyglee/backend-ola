package com.project.sparta.product.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.product.dto.ProductResponseDto;
import com.project.sparta.product.service.NaverApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Join;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(tags = {"상품"})
@RestController
@RequiredArgsConstructor
public class NaverApiController {

    private final NaverApiService naverApiService;
    @ApiOperation(value = "상품 리스트 조회",response = Join.class)
    @GetMapping("/product/search")
    public PageResponseDto<List<ProductResponseDto>> getProductSearchList(
            @RequestParam String query,
            @RequestParam int limit,
            @RequestParam int offset,
            @RequestParam String sort)  {
        return naverApiService.searchItems(query, limit, offset, sort);
    }
}