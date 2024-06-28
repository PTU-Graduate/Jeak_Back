// RegiRepositoryDao.java
package com.example.Lee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.Lee.model.RegiModel;

public interface RegiRepositoryDao extends JpaRepository<RegiModel, String> {
    boolean existsByStdNum(String stdNum);
    boolean existsByEmail(String email);
    boolean existsByMembId(String membId);

    // 기본 정보만 저장하는 메서드
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO stu_info (MEMB_ID, STD_NUM, STD_DEP_CD, NAME, SALT) VALUES (:membId, :stdNum, :stdDepCd, :name, :salt)", nativeQuery = true)
    void saveBasicInfo(String membId, String stdNum, String stdDepCd, String name, String salt);

    // membId로 정보 찾기
    @Query(value = "SELECT * FROM stu_info WHERE MEMB_ID = :membId", nativeQuery = true)
    RegiModel findByMembId(String membId);
}
