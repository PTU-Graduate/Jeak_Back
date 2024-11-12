package com.example.Lee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Lee.model.CommonResponseModel;
import com.example.Lee.dao.NoticeListDao;
import com.example.Lee.model.NoticeModel;

@Service
public class NoticeListService {

    @Autowired
    private NoticeListDao noticeDao;
    
    //공지사항 저장 메소드
    @Transactional
    public CommonResponseModel saveNotice(NoticeModel notice) {
        if (notice.getTitle() == null || notice.getContent() == null) {
            return new CommonResponseModel("01");
        }
        noticeDao.save(notice);
        return new CommonResponseModel("00");
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