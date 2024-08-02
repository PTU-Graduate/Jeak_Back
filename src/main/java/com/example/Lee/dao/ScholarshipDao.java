package com.example.Lee.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

import com.example.Lee.model.ScholarshipModel;
public interface ScholarshipDao extends JpaRepository<ScholarshipModel, Long> {

	
	// 공지사항 등록을 위한 컴스텀 쿼리 
	 @Transactional
	 @Modifying
	 @Query(value = "INSERT INTO notice_list (CRE_SEQ, MEMB_ID, TIT, CONT, CRE_DATE) VALUES (:creSEQ, :membId, :title, :content, :createdDate)", nativeQuery = true)
	void saveScholarship(String creSEQ, String memdId, String title, String content, LocalDateTime createDate);
	
}
