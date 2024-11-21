package com.example.Lee.service;

import com.example.Lee.model.CommonResponseModel;
import com.example.Lee.model.EntranceCheck;
import com.example.Lee.dao.EntranceCheckDao;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EntranceCheckService {
    private final EntranceCheckDao entranceCheckDao;

    @Autowired
    public EntranceCheckService(EntranceCheckDao entranceCheckDao) {
        this.entranceCheckDao = entranceCheckDao;
    }

    @Autowired
    private ImageFileUploadSystem imageFileUploadSystem;

    public Page<EntranceCheck> getEntrance(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return entranceCheckDao.findAllByOrderByCreDateDesc(pageable);
    }

    // 입학안내 저장 메소드
    @Transactional
    public CommonResponseModel saveEntrance(EntranceCheck entrance, String base64Image, String userId) {
        try {
            if (entrance.getTitle() == null || entrance.getContent() == null) {
                return new CommonResponseModel("01");
            }

            if (base64Image != null && !base64Image.isEmpty()) {
                byte[] imageBytes = imageFileUploadSystem.decodeBase64Image(base64Image);
                String imagePath = imageFileUploadSystem.saveImageFile(null, imageBytes, userId);
                entrance.setImgCd(imagePath);
            }

            entranceCheckDao.save(entrance);
            return new CommonResponseModel("00");
        } catch (IOException e) {
            e.printStackTrace();
            return new CommonResponseModel("02"); // 파일 저장 오류
        }
    }

    // 입학안내 수정 메소드
    @Transactional
    public CommonResponseModel updateEntrance(int creSeq, String title, String content, String imageInput) {
        EntranceCheck entrance = entranceCheckDao.findById((long) creSeq).orElse(null);
        if (entrance == null) {
            return new CommonResponseModel("01"); // 입학안내가 없으면 01 반환
        }

        String existingImagePath = entrance.getImgCd();

        try {
            // 이미지 처리
            if (imageInput != null && !imageInput.isEmpty()) {
                if (imageInput.startsWith("data:image")) {
                    // Base64 문자열 처리
                    byte[] imageBytes = imageFileUploadSystem.decodeBase64Image(imageInput);
                    String newImagePath = imageFileUploadSystem.saveImageFile(null, imageBytes, String.valueOf(creSeq));

                    // 기존 이미지 삭제
                    if (existingImagePath != null) {
                        imageFileUploadSystem.deleteImageFile(existingImagePath);
                    }

                    entrance.setImgCd(newImagePath);
                } else if (imageInput.equals(existingImagePath)) {
                    // 기존 경로와 동일한 값: 아무 작업도 하지 않음
                } else if (imageInput.equals("")) {
                    // 빈 문자열로 이미지 삭제 요청
                    if (existingImagePath != null) {
                        imageFileUploadSystem.deleteImageFile(existingImagePath);
                        entrance.setImgCd(null); // DB 경로 null로 설정
                    }
                }
            }

            // 제목 및 내용 업데이트
            entrance.setTitle(title);
            entrance.setContent(content);

            entranceCheckDao.save(entrance);

            return new CommonResponseModel("00"); // 성공
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponseModel("02"); // 이미지 처리 실패 시 02 반환
        }
    }

    // 입학안내 삭제 메소드
    @Transactional
    public CommonResponseModel deleteEntrance(int creSeq) {
        EntranceCheck entrance = entranceCheckDao.findById((long) creSeq).orElse(null);
        if (entrance == null) {
            return new CommonResponseModel("01");
        }

        // 이미지 삭제
        if (entrance.getImgCd() != null) {
            imageFileUploadSystem.deleteImageFile(entrance.getImgCd());
        }

        entranceCheckDao.delete(entrance);
        return new CommonResponseModel("00");
    }
}
