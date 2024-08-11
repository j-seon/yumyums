package com.yum.yumyums.service;

import com.yum.yumyums.dto.ImagesDTO;
import com.yum.yumyums.entity.Images;
import org.springframework.web.multipart.MultipartFile;

public interface ImagesService {
    String uploadImage(MultipartFile imgFile, String imgUrl);

    Images saveImage(ImagesDTO imagesDTO);

    Images findById(Long imgId);
}
