package com.yum.yumyums.dto;

import com.yum.yumyums.entity.Images;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImagesDTO {
    private Long imgId;
    private String imgUrl;
    private LocalDateTime uploadTime;

    public Images dtoToEntity() {
        Images image = new Images();
        image.setImgUrl(this.imgUrl);
        return image;
    }
}
