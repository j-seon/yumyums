package com.yum.yumyums.service;

import com.yum.yumyums.dto.FaqDTO;
import com.yum.yumyums.entity.Faq;
import com.yum.yumyums.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("faqService")
public class FaqServiceImpl implements FaqService {

    @Autowired
    private FaqRepository faqRepository;

    @Override
    public List<FaqDTO> findAll() {
        List<FaqDTO> returnDto =  new ArrayList<>();;
        List<Faq> findAll = faqRepository.findAll();
        for (Faq findEntity :  findAll) {
            returnDto.add(findEntity.entityToDto());
        }
        return returnDto;
    }

    @Override
    public List<String> findDistinctCategories() {
        return faqRepository.findDistinctCategories();
    }

    @Override
    public List<FaqDTO> findByCategory(String category) {
        List<FaqDTO> returnDto =  new ArrayList<>();;

        List<Faq> findAll = faqRepository.findByCategory(category);
        for (Faq findEntity :  findAll) {
            returnDto.add(findEntity.entityToDto());
        }
        return returnDto;
    }
}
