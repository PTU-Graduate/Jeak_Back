package com.example.Lee.service;

import com.example.Lee.model.BachelorCheck;
import com.example.Lee.model.CommonResponseModel;
import com.example.Lee.dao.BachelorCheckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;

@Service
public class BachelorCheckService {

    @Autowired
    private BachelorCheckDao bachelorCheckDao;

    @Autowired
    private ImageFileUploadSystem imageFileuploadSystem;

    /**
     * 학사안내 리스트 조회 메소드 (페이지네이션)
     */
    public Page<BachelorCheck> getBachelor(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bachelorCheckDao.findAllByOrderByCreDateDesc(pageable);
    }

    /**
     * 학사안내 저장 메소드
     */
    @Transactional
    public CommonResponseModel saveBachelor(BachelorCheck bachelor, String base64Image, String userId) {
        try {
            if (base64Image != null && !base64Image.isEmpty()) {
                byte[] imageBytes = imageFileuploadSystem.decodeBase64Image(base64Image);
                String imagePath = imageFileuploadSystem.saveImageFile(null, imageBytes, userId);
                bachelor.setImgCd(imagePath);
            }

            bachelorCheckDao.save(bachelor);
            return new CommonResponseModel("00");
        } catch (IOException e) {
            e.printStackTrace();
            return new CommonResponseModel("02");
        }
    }

    /**
     * 학사안내 수정 메소드
     */
    @Transactional
    public CommonResponseModel updateBachelor(int creSeq, String title, String content, String imageInput) throws IOException {
        BachelorCheck bachelor = bachelorCheckDao.findById((long) creSeq).orElse(null);
        if (bachelor == null) {
            return new CommonResponseModel("01");  // 학사안내가 없으면 01 반환
        }

        String existingImagePath = bachelor.getImgCd();

        try {
            // IMAGE 처리
            if (imageInput != null && !imageInput.isEmpty()) {
                if (imageInput.startsWith("data:image")) {
                    // 1. Base64 문자열 처리
                    byte[] imageBytes = imageFileuploadSystem.decodeBase64Image(imageInput);
                    String newImagePath = imageFileuploadSystem.saveImageFile(null, imageBytes, String.valueOf(creSeq));

                    // 기존 이미지 삭제
                    if (existingImagePath != null) {
                        imageFileuploadSystem.deleteImageFile(existingImagePath);
                    }

                    // 새로운 이미지 경로 업데이트
                    bachelor.setImgCd(newImagePath);

                } else if (imageInput.equals(existingImagePath)) {
                    // 2. 기존 경로와 동일한 값: 아무 작업도 하지 않음

                }
            }
            else if (imageInput == null || imageInput.trim().isEmpty()) {
                // 3. 빈 문자열 (""), null인 경우: 이미지 삭제 요청
                if (existingImagePath != null) {
                    
                    boolean deleteSuccess = imageFileuploadSystem.deleteImageFile(existingImagePath);
                    
                    // 이미지 파일 삭제가 성공적으로 이루어졌으면 DB에서 경로를 null로 설정
                    if (deleteSuccess) {
                        
                        bachelor.setImgCd(null);  // DB 경로 null로 설정
                    } else {
                   
                    }
                }
            }

            // 제목 및 내용 업데이트
            bachelor.setTitle(title);
            bachelor.setContent(content);

            // 학사안내 저장
            bachelorCheckDao.save(bachelor);

        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponseModel("02");  // 이미지 처리 실패 시 02 반환
        }

        return new CommonResponseModel("00");  // 성공
    }

    /**
     * 학사안내 삭제 메소드
     */
    @Transactional
    public CommonResponseModel deleteBachelor(int creSeq) {
        BachelorCheck bachelor = bachelorCheckDao.findById((long) creSeq).orElse(null);
        if (bachelor == null) {
            return new CommonResponseModel("01");
        }

        // 이미지 삭제
        if (bachelor.getImgCd() != null) {
            imageFileuploadSystem.deleteImageFile(bachelor.getImgCd());
        }

        bachelorCheckDao.delete(bachelor);
        return new CommonResponseModel("00");
    }
}
