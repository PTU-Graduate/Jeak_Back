package com.example.Lee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRsltModel {
	private String RSLT_CD;
	private String STD_NUM;
	private String STD_DEP_CD;
	private String NAME;

	public LoginRsltModel(String RSLT_CD) {
	    this.RSLT_CD = RSLT_CD;
	    this.STD_NUM = null;    // STD_NUM을 대문자로 수정
	    this.STD_DEP_CD = null; // STD_DEP_CD를 대문자로 수정
	    this.NAME = null;       // NAME을 대문자로 수정
	}


	
	// 모든 필드를 포함하는 생성자
	public LoginRsltModel(String RSLT_CD, String STD_NUM, String STD_DEP_CD, String NAME) {
		this.RSLT_CD = RSLT_CD;
		this.STD_NUM = STD_NUM;
		this.STD_DEP_CD = STD_DEP_CD;
		this.NAME = NAME;
	}

	@JsonProperty("RSLT_CD")
	public String getRSLT_CD() {
		return RSLT_CD;
	}

	public void setRSLT_CD(String RSLT_CD) {
		this.RSLT_CD = RSLT_CD;
	}

	@JsonProperty("STD_NUM")
	public String getSTD_NUM() {
		return STD_NUM;
	}

	@JsonProperty("STD_DEP_CD")
	public String getSTD_DEP_CD() {
		return STD_DEP_CD;
	}

	@JsonProperty("NAME")
	public String getNAME() {
		return NAME;
	}
}
