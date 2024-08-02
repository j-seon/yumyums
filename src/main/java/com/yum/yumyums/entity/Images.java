package com.yum.yumyums.entity;

import com.yum.yumyums.dto.ImagesDTO;
import com.yum.yumyums.entity.seller.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgUrl;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime uploadTime;

    @OneToMany(mappedBy = "images")
    private List<Store> stores;

    public ImagesDTO entityToDto(){
        ImagesDTO imagesDTO = new ImagesDTO();
        imagesDTO.setImgId(this.id);
        imagesDTO.setImgUrl(this.imgUrl);
        imagesDTO.setUploadTime(this.uploadTime);
        return imagesDTO;
    }
}
