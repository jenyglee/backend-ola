package com.project.sparta.product.controller;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.product.dto.ProductResponseDto;
import com.project.sparta.product.service.NaverApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NaverApiController {

    private final NaverApiService naverApiService;

    @GetMapping("/product/search")
    public PageResponseDto<List<ProductResponseDto>> getProductList(
            @RequestParam String query,
            @RequestParam int limit,
            @RequestParam int offset
    )  {
        return naverApiService.searchItems(query, limit, offset, "dsc");
    }
}