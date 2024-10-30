package com.example.Lee.service;

import com.example.Lee.model.EntranceCheck;
import com.example.Lee.dao.EntranceCheckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntranceCheckService {
    private final EntranceCheckDao entranceCheckDao;

    @Autowired
    public EntranceCheckService(EntranceCheckDao entranceCheckDao) {
        this.entranceCheckDao = entranceCheckDao;
    }

    public Page<EntranceCheck> getEntrance(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return entranceCheckDao.findAllByOrderByCreDateDesc(pageable);
    }
    
    //입학안내 저장 메소드
    @Transactional
    public EntranceCheck saveEntrance(EntranceCheck entrance) {
        if (entrance.getTitle() == null || entrance.getContent() == null) {
            throw new IllegalArgumentException("Title and Content cannot be null");
        }
        return entranceCheckDao.save(entrance);
    }
}
