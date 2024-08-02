package com.example.Lee.dao;

import com.example.Lee.model.NoticeCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCheckDao extends JpaRepository<NoticeCheck, Long> {
    Page<NoticeCheck> findAllByOrderByCreDateDesc(Pageable pageable);
}
