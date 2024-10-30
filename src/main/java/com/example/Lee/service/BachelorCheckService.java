package com.example.Lee.service;

import com.example.Lee.model.BachelorCheck;
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
    public BachelorCheck saveBachelor(BachelorCheck bachelor) {
        if (bachelor.getTitle() == null || bachelor.getContent() == null) {
            throw new IllegalArgumentException("Title and Content cannot be null");
        }
        return bachelorCheckDao.save(bachelor);
    }
}
