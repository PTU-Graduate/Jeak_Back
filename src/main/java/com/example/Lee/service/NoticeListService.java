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
    
    //공지사항 업데이트 메소드
    @Transactional
    public CommonResponseModel updateNotice(int creSeq, String title, String content) {
    	NoticeModel notice = noticeDao.findById((long) creSeq).orElse(null);
    	if (notice == null) {
    		return new CommonResponseModel("01");
    	}
    	
    	notice.setTitle(title);
    	notice.setContent(content);
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