package com.example.Lee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 엔티티를 나타내며, 데이터베이스의 stu_info 테이블에 매핑됨 재경 test
@Entity
@Table(name = "stu_info")
public class RegiModel {

	@Id // 기본 키(PK) 필드임을 나타냄
	private String membId;

	@Column(unique = true) // 학번은 유니크(고유)해야 함
	private String stdNum;

	@Column(unique = true) // 이메일 또한 유니크(고유)해야 함
	private String email;

	private String pass; // 패스워드
	private String stdDepCd; // 학과 코드
	private String name; // 이름
	private String salt;

	// 기본 생성자: JPA에서 엔티티 클래스는 기본 생성자를 가지고 있어야 함
	public RegiModel() {
	}

	// 모든 속성을 포함하는 생성자
	public RegiModel(String membId, String stdNum, String email, String pass, String stdDepCd, String name, String salt) {
		this.membId = membId;
		this.stdNum = stdNum;
		this.email = email;
		this.pass = pass;
		this.stdDepCd = stdDepCd;
		this.name = name;
		this.salt = salt;
	}

	// Getter and Setter 메서드: 엔티티의 속성에 접근하기 위한 메서드
	@JsonProperty("MEMB_ID")
	public String getMembId() {
		return membId;
	}

	public void setMembId(String membId) {
		this.membId = membId;
	}

	@JsonProperty("STD_NUM")
	public String getStdNum() {
		return stdNum;
	}

	public void setStdNum(String stdNum) {
		this.stdNum = stdNum;
	}
	@JsonProperty("EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@JsonProperty("PASS")
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	@JsonProperty("STD_DEP_CD")
	public String getStdDepCd() {
		return stdDepCd;
	}

	public void setStdDepCd(String stdDepCd) {
		this.stdDepCd = stdDepCd;
	}
	@JsonProperty("NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	@JsonProperty("SALT")
	public String getSalt() {
		return salt;
	}
	
}