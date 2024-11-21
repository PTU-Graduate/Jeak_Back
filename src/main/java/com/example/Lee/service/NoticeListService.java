package com.example.Lee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Lee.model.CommonResponseModel;
import com.example.Lee.dao.NoticeListDao;
import com.example.Lee.model.NoticeModel;

import java.io.IOException;

@Service
public class NoticeListService {

    @Autowired
    private NoticeListDao noticeDao;

    @Autowired
    private ImageFileUploadSystem imageFileUploadSystem;

    /**
     * 공지사항 저장 메소드
     */
    @Transactional
    public CommonResponseModel saveNotice(NoticeModel notice, String base64Image, String userId) {
        try {
            if (base64Image != null && !base64Image.isEmpty()) {
                byte[] imageBytes = imageFileUploadSystem.decodeBase64Image(base64Image);
                String imagePath = imageFileUploadSystem.saveImageFile(null, imageBytes, userId);
                notice.setImgCd(imagePath);
            }

            noticeDao.save(notice);
            return new CommonResponseModel("00");
        } catch (IOException e) {
            e.printStackTrace();
            return new CommonResponseModel("02");
        }
    }

    /**
     * 공지사항 수정 메소드
     */
    @Transactional
    public CommonResponseModel updateNotice(int creSeq, String title, String content, String imageInput) throws IOException {
        NoticeModel notice = noticeDao.findById((long) creSeq).orElse(null);
        if (notice == null) {
            return new CommonResponseModel("01");  // 공지사항이 없으면 01 반환
        }

        String existingImagePath = notice.getImgCd();
        

        try {
            // IMAGE 처리
            if (imageInput != null && !imageInput.isEmpty()) {
                if (imageInput.startsWith("data:image")) {
                    // 1. Base64 문자열 처리
                	
                    byte[] imageBytes = imageFileUploadSystem.decodeBase64Image(imageInput);
                    String newImagePath = imageFileUploadSystem.saveImageFile(null, imageBytes, String.valueOf(creSeq));

                    // 기존 이미지 삭제
                    if (existingImagePath != null) {
                        imageFileUploadSystem.deleteImageFile(existingImagePath);
                    }

                    // 새로운 이미지 경로 업데이트
                    notice.setImgCd(newImagePath);

                } else if (imageInput.equals(existingImagePath)) {
                    // 2. 기존 경로와 동일한 값: 아무 작업도 하지 않음
                	
                } 

            }
            else if (imageInput == null || imageInput.trim().isEmpty()) {
                // 3. 빈 문자열 (""), null인 경우: 이미지 삭제 요청
                if (existingImagePath != null) {
                    
                    boolean deleteSuccess = imageFileUploadSystem.deleteImageFile(existingImagePath);
                    
                    // 이미지 파일 삭제가 성공적으로 이루어졌으면 DB에서 경로를 null로 설정
                    if (deleteSuccess) {
                        
                        notice.setImgCd(null);  // DB 경로 null로 설정
                    } else {
                   
                    }
                }
            }
        
            // 제목 및 내용 업데이트
            notice.setTitle(title);
            notice.setContent(content);

            // 공지사항 저장
            noticeDao.save(notice);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResponseModel("02");  // 이미지 처리 실패 시 02 반환
        }

        return new CommonResponseModel("00");  // 성공
    }


    /**
     * 공지사항 삭제 메소드
     */
    @Transactional
    public CommonResponseModel deleteNotice(int creSeq) {
        NoticeModel notice = noticeDao.findById((long) creSeq).orElse(null);
        if (notice == null) {
            return new CommonResponseModel("01");
        }

        // 이미지 삭제
        if (notice.getImgCd() != null) {
            imageFileUploadSystem.deleteImageFile(notice.getImgCd());
        }

        noticeDao.delete(notice);
        return new CommonResponseModel("00");
    }
}
