package com.example.Lee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    
    // 공지사항 저장 메소드
    @Transactional
    public CommonResponseModel saveNotice(NoticeModel notice, MultipartFile imageFile, String userId) {
        try {
            if (notice.getTitle() == null || notice.getContent() == null) {
                return new CommonResponseModel("01");
            }
            
            // 이미지 파일이 있는 경우 저장하고 경로를 설정
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = imageFileUploadSystem.saveImageFile(imageFile, userId);
                notice.setImgCd(imagePath);  // NoticeModel에 이미지 경로 저장
            }

            noticeDao.save(notice);
            return new CommonResponseModel("00");
        } catch (IOException e) {
            e.printStackTrace();
            return new CommonResponseModel("02");  // 파일 저장 오류 코드
        }
    }
    
    @Transactional
    public CommonResponseModel updateNotice(int creSeq, String title, String content, MultipartFile imageFile, String imageUrl) throws IOException {
        NoticeModel notice = noticeDao.findById((long) creSeq).orElse(null);
        if (notice == null) {
            return new CommonResponseModel("01");
        }

        // 기존 이미지 경로
        String existingImagePath = notice.getImgCd();

        // 이미지 처리
        if (imageFile != null && !imageFile.isEmpty()) {
            // 새 이미지를 업로드
            String newImagePath = imageFileUploadSystem.saveImageFile(imageFile, String.valueOf(creSeq));

            // 기존 이미지 삭제
            if (existingImagePath != null) {
                imageFileUploadSystem.deleteImageFile(existingImagePath);
            }

            // 새로운 이미지 경로 업데이트
            notice.setImgCd(newImagePath);

        } else if (imageUrl == null) {
            // 클라이언트에서 null을 보낸 경우 (이미지 삭제 요청)
            if (existingImagePath != null) {
                imageFileUploadSystem.deleteImageFile(existingImagePath);
                notice.setImgCd(null);
            }
        }

        // 제목 및 내용 업데이트
        notice.setTitle(title);
        notice.setContent(content);

        // 저장
        noticeDao.save(notice);

        return new CommonResponseModel("00");
    }



    
    //공지사항 삭제 메소드
    @Transactional
    public CommonResponseModel deleteNotice(int creSeq) {
    	NoticeModel notice = noticeDao.findById((long) creSeq).orElse(null);
    	if (notice == null) {
    		return new CommonResponseModel("01");
    	}
    	
    	noticeDao.delete(notice);
    	return new CommonResponseModel("00");
    }
}