package com.example.Lee.service;

import com.example.Lee.model.ScholarCheck;
import com.example.Lee.dao.ScholarCheckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScholarCheckService {
    private final ScholarCheckDao scholarCheckDao;

    @Autowired
    public ScholarCheckService(ScholarCheckDao scholarCheckDao) {
        this.scholarCheckDao = scholarCheckDao;
    }

    public Page<ScholarCheck> getScholar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return scholarCheckDao.findAllByOrderByCreDateDesc(pageable);
    }
    
    //장학안내 저장 메소드
    @Transactional
    public ScholarCheck saveScholar(ScholarCheck scholar) {
        if (scholar.getTitle() == null || scholar.getContent() == null) {
            throw new IllegalArgumentException("Title and Content cannot be null");
        }
        return scholarCheckDao.save(scholar);
    }
}
