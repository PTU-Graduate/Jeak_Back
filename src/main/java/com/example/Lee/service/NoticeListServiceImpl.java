package com.example.Lee.service;

import com.example.Lee.model.NoticeModel;
import com.example.Lee.dao.NoticeListDao;
import com.example.Lee.service.NoticeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeListServiceImpl implements NoticeListService {

    private final NoticeListDao noticeListDao;

    @Autowired
    public NoticeListServiceImpl(NoticeListDao noticeListDao) {
        this.noticeListDao = noticeListDao;
    }

    @Override
    public List<NoticeModel> getAllNotices() {
        return noticeListDao.findAll();
    }

    @Override
    public NoticeModel getNoticeById(String membId) {
        return noticeListDao.findById(membId).orElseThrow(() -> new RuntimeException("Notice not found"));
    }

    @Override
    public NoticeModel createNotice(NoticeModel notice) {
        notice.setCreatedDate(LocalDateTime.now());
        return noticeListDao.save(notice);
    }

    @Override
    public NoticeModel updateNotice(String membId, NoticeModel notice) {
        NoticeModel existingNotice = getNoticeById(membId);
        existingNotice.setTitle(notice.getTitle());
        existingNotice.setContent(notice.getContent());
        return noticeListDao.save(existingNotice);
    }

    @Override
    public void deleteNotice(String membId) {
        noticeListDao.deleteById(membId);
    }
}
