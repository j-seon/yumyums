package com.yum.yumyums.service;


import com.yum.yumyums.dto.FaqDTO;

import java.util.List;

public interface FaqService {
    List<FaqDTO> findAll();
    List<String> findDistinctCategories();
    List<FaqDTO> findByCategory(String category);
}
