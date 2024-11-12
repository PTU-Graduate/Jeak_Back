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

@Service
public class BachelorCheckService {
    private final BachelorCheckDao bachelorCheckDao;

    @Autowired
    public BachelorCheckService(BachelorCheckDao bachelorCheckDao) {
        this.bachelorCheckDao = bachelorCheckDao;
    }

    public Page<BachelorCheck> getBachelor(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bachelorCheckDao.findAllByOrderByCreDateDesc(pageable);
    }
    
    //학사안내 저장 메소드
    @Transactional
    public CommonResponseModel saveBachelor(BachelorCheck bachelor) {
        if (bachelor.getTitle() == null || bachelor.getContent() == null) {
        	return new CommonResponseModel("01");
        }
        bachelorCheckDao.save(bachelor);
        return new CommonResponseModel("00");
    }
    
    //학사안내 업데이트 메소드
    @Transactional
    public CommonResponseModel updateBachelor(int creSeq, String title, String content) {
    	BachelorCheck bachelor = bachelorCheckDao.findById((long) creSeq).orElse(null);
    	if (bachelor == null) {
    		return new CommonResponseModel("01");
    	}
    	
    	bachelor.setTitle(title);
    	bachelor.setContent(content);
    	bachelorCheckDao.save(bachelor);
    	
    	return new CommonResponseModel("00");
    }
    
    //학사안내 삭제 메소드
    @Transactional
    public CommonResponseModel deleteBachelor(int creSeq) {
    	BachelorCheck bachelor = bachelorCheckDao.findById((long) creSeq).orElse(null);
    	if (bachelor == null) {
    		return new CommonResponseModel("01");
    	}
    	
    	bachelorCheckDao.delete(bachelor);
    	return new CommonResponseModel("00");
    }
}