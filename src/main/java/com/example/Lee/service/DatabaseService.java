package com.example.Lee.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseService {

	// JdbcTemplate 주입
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public DatabaseService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// 애플리케이션 시작 시 실행되는 메서드
	@PostConstruct
	public void initialize() {
		System.out.println("데이터베이스 연결됨");
	}

	// 특정 테이블의 모든 데이터를 가져오는 메서드
	public List<Map<String, Object>> getAllDataFromTable(String tableName) {
		// SQL 쿼리 생성
		String sql = "SELECT * FROM " + tableName;

		// JdbcTemplate을 사용하여 쿼리 실행하여 데이터 조회
		List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);

		return data;
	}

	// 데이터베이스 연결 여부 확인하는 메서드
	private boolean isDatabaseConnected() {
		try {
			// 임의의 쿼리 실행하여 데이터베이스 연결 상태 확인
			jdbcTemplate.execute("SELECT 1");
			return true; // 연결 성공
		} catch (Exception e) {
			return false; // 연결 실패
		}
	}
}
