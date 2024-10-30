package com.example.Lee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Lee.model.StuInfo;

public interface StuInfoDao extends JpaRepository<StuInfo, String> {
}
