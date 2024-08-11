package com.yum.yumyums.repository;

import com.yum.yumyums.entity.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long> {
    Images findByImgUrl(String imgUrl);

}
