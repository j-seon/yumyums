package com.yum.yumyums.service;

import com.yum.yumyums.dto.ImagesDTO;
import com.yum.yumyums.entity.Images;
import com.yum.yumyums.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.yum.yumyums.util.ImageDefaultUrl.DEFAULT_IMAGE_URL;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService{

    private final ImagesRepository imagesRepository;
    private final Logger logger = LoggerFactory.getLogger(ImagesServiceImpl.class);

    @Value("${file.upload-dir}") // 혼동주의! : Spring의 Value 어노테이션이다.(Lombok 아님)
    private String uploadDir;



    @Override
    public String uploadImage(MultipartFile imgFile, String imgUrl) {
        if(imgUrl == null){
            return DEFAULT_IMAGE_URL;
        }

        logger.info("이미지 업로드 시작: {}", imgUrl);
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
            logger.info("디렉토리 생성: {}", destinationDir.getPath());
        }

        //파일명 중복처리
        String newImgUrl = imgUrl;
            int count = 1;
            while (new File(uploadDir + newImgUrl).exists()) {
                newImgUrl = dirPath + baseName + "(" + count + ")" + extension; // 파일경로 재설정
                count++;
            }

        try {
            imgFile.transferTo(new File(uploadDir + newImgUrl));
            logger.info("이미지 업로드 완료: {}");
        } catch (IOException e) {
            logger.error("이미지 업로드 실패: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        // -----이미지 업로드 완료------
        return newImgUrl;

    }

    @Override
    public Images saveImage(ImagesDTO imagesDTO) {
        String imgUrl = imagesDTO.getImgUrl();
        logger.info("이미지 세이브 시작: {}", imgUrl);
        Images duplicatedImg = imagesRepository.findByImgUrl(imgUrl);
        if(duplicatedImg != null){
            logger.info("중복 이미지 발견: {}", imgUrl);
            return duplicatedImg;

        } else {
            logger.info("이미지 데이터베이스에 저장: {}", imgUrl);
            return imagesRepository.save(imagesDTO.dtoToEntity());
        }
    }

}
