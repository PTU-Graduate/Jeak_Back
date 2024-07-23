package com.example.Lee.dao;

import com.example.Lee.model.NoticeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeListDao extends JpaRepository<NoticeModel, String> {
}