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
    private ImageFileUploadSystem imageFileuploadSystem;
    
    public Page<ScholarCheck> getScholar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scholarCheckDao.findAllByOrderByCreDateDesc(pageable);
    }
    
    //장학안내 저장 메소드
    @Transactional
    public CommonResponseModel saveScholar(ScholarCheck scholar, MultipartFile imageFile, String userId) {
       try {
    	if (scholar.getTitle() == null || scholar.getContent() == null) {
        	return new CommonResponseModel("01");
        }
    	
    	if (imageFile != null && !imageFile.isEmpty()) {
    		String imagePath = imageFileuploadSystem.saveImageFile(imageFile, userId);
    		scholar.setImgCd(imagePath);
    	}
    	
    	scholarCheckDao.save(scholar);
        return new CommonResponseModel("00");
    } catch (IOException e) {
    	e.printStackTrace();
    	return new CommonResponseModel("02");
    }
   }
    
    //장학안내 업데이트 메소드
    @Transactional
    public CommonResponseModel updateScholar(int creSeq, String title, String content) {
    	ScholarCheck scholar = scholarCheckDao.findById((long) creSeq).orElse(null);
    	if (scholar == null) {
    		return new CommonResponseModel("01");
    	}
    	
    	scholar.setTitle(title);
    	scholar.setContent(content);
    	scholarCheckDao.save(scholar);
    	
    	return new CommonResponseModel("00");
    }
    
    //장학안내 삭제 메소드
    @Transactional
    public CommonResponseModel deleteScholar(int creSeq) {
    	ScholarCheck scholar = scholarCheckDao.findById((long) creSeq).orElse(null);
    	if (scholar == null) {
    		return new CommonResponseModel("01");
    	}
    	
    	scholarCheckDao.delete(scholar);
    	return new CommonResponseModel("00");
    }
}
