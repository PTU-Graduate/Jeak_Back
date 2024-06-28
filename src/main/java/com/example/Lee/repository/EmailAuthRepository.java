package com.example.Lee.repository;

import java.util.Optional; // Optional 클래스를 사용하여 null을 안전하게 처리

import org.springframework.data.jpa.repository.JpaRepository; // JpaRepository를 사용하여 기본 CRUD 작업을 지원

import com.example.Lee.model.EmailAuthModel; // EmailAuthModel 클래스를 가져옴

// EmailAuthRepository 인터페이스는 JpaRepository를 확장하여 EmailAuthModel 엔티티에 대한 데이터 접근을 담당
public interface EmailAuthRepository extends JpaRepository<EmailAuthModel, String> {

	// 'membId'로 EmailAuthModel 엔티티를 검색하는 메서드
	Optional<EmailAuthModel> findByMembId(String membId);
}
