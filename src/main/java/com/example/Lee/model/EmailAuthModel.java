package com.example.Lee.model;

import java.time.LocalDateTime; // Java에서 날짜와 시간을 나타내기 위한 클래스

import jakarta.persistence.Entity; // JPA 엔티티를 나타내는 어노테이션
import jakarta.persistence.Id; // 엔티티의 기본 키를 나타내는 어노테이션
import jakarta.persistence.Table; // 엔티티와 매핑할 테이블을 지정하는 어노테이션

@Entity // 이 클래스가 JPA 엔티티임을 나타냄
@Table(name = "stu_info") // 이 엔티티가 데이터베이스의 'stu_info' 테이블에 매핑됨을 나타냄
public class EmailAuthModel {

	@Id // 'membId' 필드가 이 엔티티의 기본 키임을 나타냄
	private String membId; // 사용자가 입력한 아이디
	private String email; // 사용자가 입력한 이메
	private String creCode; // 사용자가 입력 하여야 할 인증 코드
	private LocalDateTime creCodeTime; // 사용자가 인증을 요청한 시
	private String creCon; // 사용자 인증 정보

	// 'membId' 필드의 getter 메서드
	public String getMembId() {
		return membId;
	}

	// 'membId' 필드의 setter 메서드
	public void setMembId(String membId) {
		this.membId = membId;
	}

	// 'email' 필드의 getter 메서드
	public String getEmail() {
		return email;
	}

	// 'email' 필드의 setter 메서드
	public void setEmail(String email) {
		this.email = email;
	}

	// 'creCode' 필드의 getter 메서드
	public String getCreCode() {
		return creCode;
	}

	// 'creCode' 필드의 setter 메서드
	public void setCreCode(String creCode) {
		this.creCode = creCode;
	}

	// 'creCodeTime' 필드의 getter 메서드
	public LocalDateTime getCreCodeTime() {
		return creCodeTime;
	}

	// 'creCodeTime' 필드의 setter 메서드
	public void setCreCodeTime(LocalDateTime creCodeTime) {
		this.creCodeTime = creCodeTime;
	}

	// 'creCon' 필드의 getter 메서드
	public String getCreCon() {
		return creCon;
	}

	// 'creCon' 필드의 setter 메서드
	public void setCreCon(String creCon) {
		this.creCon = creCon;
	}
}
