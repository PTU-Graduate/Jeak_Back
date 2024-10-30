package com.example.Lee.dao;

import com.example.Lee.model.EntranceCheck;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EntranceCheckDao extends JpaRepository<EntranceCheck, Long> {
    Page<EntranceCheck> findAllByOrderByCreDateDesc(Pageable pageable);
    
    //입학안내 등록을 위한 커스텀 쿼리
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO entrance_list (CRE_SEQ, MEMB_ID, TIT, CONT, CRE_DATE) VALUES (:creSEQ, :membId, :title, :content, :createdDate)", nativeQuery = true)
    void saveEntrance(String creSEQ, String membId, String title, String content, LocalDateTime createdDate);
}
