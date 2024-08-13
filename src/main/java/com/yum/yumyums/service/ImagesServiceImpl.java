package com.yum.yumyums.service;

import com.yum.yumyums.dto.ImagesDTO;
import com.yum.yumyums.entity.Images;
import com.yum.yumyums.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.yum.yumyums.util.ImageDefaultUrl.*;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService{

    private final ImagesRepository imagesRepository;

    @Value("${file.upload-dir}") // 혼동주의! : Spring의 Value 어노테이션이다.(Lombok 아님)
    private String uploadDir;



    @Override
    public String uploadImage(MultipartFile imgFile, String imgUrl) {
        if(imgUrl == null){
            return DEFAULT_IMAGE_FILENAME;
        }

        // -----이미지 업로드 시작-----
        int lastSlash = imgUrl.lastIndexOf("/");
        String dirPath = imgUrl.substring(0, lastSlash+1);
        String originalFilename = imgUrl.substring(lastSlash+1);

        String baseName = originalFilename.substring(0, originalFilename.lastIndexOf(".")); // 파일명
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        File destinationDir = new File(uploadDir+dirPath);

        // 디렉토리 존재 여부 확인
        if (!destinationDir.exists()) {
            destinationDir.mkdirs(); // 디렉토리 생성
        }

        //파일명 중복처리
        if(!imgUrl.equals(DEFAULT_IMAGE_FILENAME)
                && !imgUrl.equals(DEFAULT_MEMBER_IMAGE)
                && !imgUrl.equals(DEFAULT_STORE_IMAGE)
                && !imgUrl.equals(DEFAULT_MENU_IMAGE)){

            String newImgUrl = imgUrl;
            int count = 1;
            while (new File(uploadDir + newImgUrl).exists()) {
                newImgUrl = dirPath + baseName + "(" + count + ")" + extension; // 파일경로 재설정
                count++;
            }

            try {
                imgFile.transferTo(new File(uploadDir + newImgUrl));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // -----이미지 업로드 완료------
            return newImgUrl;
        }

        return imgUrl;

    }

    @Override
    public Images saveImage(ImagesDTO imagesDTO) {
        String imgUrl = imagesDTO.getImgUrl();
        Images duplicatedImg = imagesRepository.findByImgUrl(imgUrl);
        if(duplicatedImg != null){
            return duplicatedImg;
        } else {
            return imagesRepository.save(imagesDTO.dtoToEntity());
        }
    }

    @Override
    public Images findById(Long imgId) {
        Optional<Images> images =  imagesRepository.findById(imgId);

        return images.get();
    }

}
