package com.yum.yumyums.dto;

import com.yum.yumyums.entity.Images;
import lombok.Data;

import java.time.LocalDateTime;

import static com.yum.yumyums.util.ImageDefaultUrl.DEFAULT_IMAGE_FILENAME;

@Data
public class ImagesDTO {
    private Long imgId;
    private String imgUrl;
    private LocalDateTime uploadTime;

    public Images dtoToEntity() {
        Images image = new Images();
        if(this.imgUrl == null) {
            this.imgUrl = DEFAULT_IMAGE_FILENAME;
        }
        image.setId(this.imgId);
        image.setImgUrl(this.imgUrl);
        return image;
    }
}
