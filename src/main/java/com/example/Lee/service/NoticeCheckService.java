package com.example.Lee.service;

import com.example.Lee.model.NoticeCheck;
import com.example.Lee.dao.NoticeCheckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeCheckService {
    private final NoticeCheckDao noticeCheckDao;

    @Autowired
    public NoticeCheckService(NoticeCheckDao noticeCheckDao) {
        this.noticeCheckDao = noticeCheckDao;
    }

    public Page<NoticeCheck> getNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return noticeCheckDao.findAllByOrderByCreDateDesc(pageable);
    }
}
