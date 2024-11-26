package com.example.Lee.dao;

import com.example.Lee.model.NoticeCheck;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeCheckDao extends JpaRepository<NoticeCheck, Long> {
    // CRE_SEQ 기준 내림차순으로 정렬하여 데이터 반환
    Page<NoticeCheck> findAllByOrderByCreSeqDesc(Pageable pageable);
}
