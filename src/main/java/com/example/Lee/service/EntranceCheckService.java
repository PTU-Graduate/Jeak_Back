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
    private ImageFileUploadSystem imageFileuploadSystem;

    public Page<EntranceCheck> getEntrance(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return entranceCheckDao.findAllByOrderByCreDateDesc(pageable);
    }
    
    //입학안내 저장 메소드
    @Transactional
    public CommonResponseModel saveEntrance(EntranceCheck entrance, MultipartFile imageFile, String userId) {
       try {
    	if (entrance.getTitle() == null || entrance.getContent() == null) {
            return new CommonResponseModel("01");
        }
        
        if (imageFile != null && !imageFile.isEmpty()) {
        	String imagePath = imageFileuploadSystem.saveImageFile(imageFile, userId);
        	entrance.setImgCd(imagePath);
        }
        
        entranceCheckDao.save(entrance);
        return new CommonResponseModel("00");
    } catch (IOException e) {
    	e.printStackTrace();
    	return new CommonResponseModel("02"); //파일저장오류
    }
  }
    
    //학사안내 업데이트 메소드
    @Transactional
    public CommonResponseModel updateEntrance(int creSeq, String title, String content) {
    	EntranceCheck entrance = entranceCheckDao.findById((long) creSeq).orElse(null);
    	if (entrance == null) {
    		return new CommonResponseModel("01");
    	}
    	
    	entrance.setTitle(title);
    	entrance.setContent(content);
    	entranceCheckDao.save(entrance);
    	
    	return new CommonResponseModel("00");
    }
    
    //학사안내 삭제 메소드
    @Transactional
    public CommonResponseModel deleteEntrance(int creSeq) {
    	EntranceCheck entrance = entranceCheckDao.findById((long) creSeq).orElse(null);
    	if (entrance == null) {
    		return new CommonResponseModel("01");
    	}
    	
    	entranceCheckDao.delete(entrance);
    	return new CommonResponseModel("00");
    }
}
