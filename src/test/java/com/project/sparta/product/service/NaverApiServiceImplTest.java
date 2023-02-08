package com.project.sparta.product.service;

import com.project.sparta.common.dto.PageResponseDto;
import com.project.sparta.product.dto.ProductResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NaverApiServiceImplTest {
    @Autowired
    NaverApiService naverApiService;

    @Test
    void searchItems() {
        PageResponseDto<List<ProductResponseDto>> results = naverApiService.searchItems("macbook", 30, 1, "dsc");
        List<ProductResponseDto> data = results.getData();
        int currentPage = results.getCurrentPage();

        assertThat(data.size()).isEqualTo(30);
        assertThat(currentPage).isEqualTo(1);
    }

}