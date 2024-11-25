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

    // CRE_SEQ 기준 내림차순으로 최신 글을 반환하도록 수정
    public Page<NoticeCheck> getNotices(int page, int size) {
        Pageable pageable = PageRequest.of(page, size); // 페이지와 크기 지정
        return noticeCheckDao.findAllByOrderByCreSeqDesc(pageable); // CRE_SEQ 기준으로 내림차순 정렬
    }
}
