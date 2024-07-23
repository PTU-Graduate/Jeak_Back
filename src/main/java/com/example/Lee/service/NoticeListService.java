package com.example.Lee.service;

import com.example.Lee.model.NoticeModel;

import java.util.List;

public interface NoticeListService {
    List<NoticeModel> getAllNotices();
    NoticeModel getNoticeById(String membId);
    NoticeModel createNotice(NoticeModel notice);
    NoticeModel updateNotice(String id, NoticeModel notice);
    void deleteNotice(String id);
}