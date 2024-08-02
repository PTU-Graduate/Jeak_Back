package com.example.Lee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Lee.model.ScholarshipModel;

public interface ScholarshipDao extends JpaRepository<ScholarshipModel, Long> {
    // 기본 제공되는 save 메서드를 사용합니다.
}
