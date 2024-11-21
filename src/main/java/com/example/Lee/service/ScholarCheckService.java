package com.example.Lee.service;

import com.example.Lee.model.CommonResponseModel;
import com.example.Lee.model.ScholarCheck;
import com.example.Lee.dao.ScholarCheckDao;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ScholarCheckService {
    private final ScholarCheckDao scholarCheckDao;

    @Autowired
    public ScholarCheckService(ScholarCheckDao scholarCheckDao) {
        this.scholarCheckDao = scholarCheckDao;
    }

    @Autowired
    private ImageFileUploadSystem imageFileUploadSystem;

    public Page<ScholarCheck> getScholar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scholarCheckDao.findAllByOrderByCreDateDesc(pageable);
    }

    // 장학안내 저장 메소드
    @Transactional
    public CommonResponseModel saveScholar(ScholarCheck scholar, String base64Image, String userId) {
        try {
            if (scholar.getTitle() == null || scholar.getContent() == null) {
                return new CommonResponseModel("01");
            }

            if (base64Image != null && !base64Image.isEmpty()) {
                byte[] imageBytes = imageFileUploadSystem.decodeBase64Image(base64Image);
                String imagePath = imageFileUploadSystem.saveImageFile(null, imageBytes, userId);
                scholar.setImgCd(imagePath);
            }

            scholarCheckDao.save(scholar);
            return new CommonResponseModel("00");
        } catch (IOException e) {
            e.printStackTrace();
            return new CommonResponseModel("02"); // 파일 저장 오류
        }
    }

    // 장학안내 수정 메소드
    @Transactional
    public CommonResponseModel updateScholar(int creSeq, String title, String content, String imageInput) {
        ScholarCheck scholar = scholarCheckDao.findById((long) creSeq).orElse(null);
        if (scholar == null) {
            return new CommonResponseModel("01"); // 장학안내가 없으면 01 반환
        }

        String existingImagePath = scholar.getImgCd();

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

                    scholar.setImgCd(newImagePath);
                } else if (imageInput.equals(existingImagePath)) {
                    // 기존 경로와 동일한 값: 아무 작업도 하지 않음
                } 
            }
            else if (imageInput == null || imageInput.trim().isEmpty()) {
                // 3. 빈 문자열 (""), null인 경우: 이미지 삭제 요청
                if (existingImagePath != null) {
                    
                    boolean deleteSuccess = imageFileUploadSystem.deleteImageFile(existingImagePath);
                    
                    // 이미지 파일 삭제가 성공적으로 이루어졌으면 DB에서 경로를 null로 설정
                    if (deleteSuccess) {
                        
                        scholar.setImgCd(null);  // DB 경로 null로 설정
                    } else {
                   
                    }
                }
            }
            // 제목 및 내용 업데이트
            scholar.setTitle(title);
            scholar.setContent(content);

            scholarCheckDao.save(scholar);
            return new CommonResponseModel("00"); // 성공
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponseModel("02"); // 이미지 처리 실패 시 02 반환
        }
    }

    // 장학안내 삭제 메소드
    @Transactional
    public CommonResponseModel deleteScholar(int creSeq) {
        ScholarCheck scholar = scholarCheckDao.findById((long) creSeq).orElse(null);
        if (scholar == null) {
            return new CommonResponseModel("01"); // 장학안내가 없으면 01 반환
        }

        // 이미지 삭제
        if (scholar.getImgCd() != null) {
            imageFileUploadSystem.deleteImageFile(scholar.getImgCd());
        }

        scholarCheckDao.delete(scholar);
        return new CommonResponseModel("00"); // 성공
    }
}
