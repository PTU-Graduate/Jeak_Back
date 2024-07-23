package com.example.Lee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Lee.dao.NoticeListDao;
import com.example.Lee.model.NoticeModel;

@Service
public class NoticeListService {

    @Autowired
    private NoticeListDao noticeDao;

    @Transactional
    public NoticeModel saveNotice(NoticeModel notice) {
        if (notice.getTitle() == null || notice.getContent() == null) {
            throw new IllegalArgumentException("Title and Content cannot be null");
        }
        return noticeDao.save(notice);
    }
}