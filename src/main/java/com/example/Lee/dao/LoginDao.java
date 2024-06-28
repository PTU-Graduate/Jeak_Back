package com.example.Lee.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Lee.model.UserModel;

//JpaRepository 인터페이스를 상속받아 사용자 정보에 대한 데이터 액세스를 처리하는 LoginDao 인터페이스를 정의합니다 에러 무시 하면 됨.
public interface LoginDao extends JpaRepository<UserModel, Long> {

	// 회원 아이디를 기반으로 사용자 정보를 조회하는 메서드를 선언합니다.
	UserModel findByMembId(String membId);
}
