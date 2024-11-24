package com.example.Lee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicUserDataSave {

	@JsonProperty("RSLT_CD")
	private String RSLT_CD;
	@JsonProperty("MEMB_ID")
	private String MEMB_ID;
	@JsonProperty("SALT")
	private String SALT;
	
	public BasicUserDataSave() {}
	
	public BasicUserDataSave(String RSLT_CD, String MEMB_ID, String SALT) {
		this.RSLT_CD = RSLT_CD;
		this.SALT = SALT;
		this.MEMB_ID = MEMB_ID;
	}
	
	public void setRSLT_CD (String RSLT_CD) {
		this.RSLT_CD = RSLT_CD;
	}
	public void setMEMB_ID (String MEMB_ID) {
		this.MEMB_ID = MEMB_ID;
	}
	public void setSALT(String SALT) {
		this.SALT = SALT;
	}
	@JsonProperty("RSLT_CD")
	public String getRSLT_CD() {
		return RSLT_CD;
	}
	@JsonProperty("SALT")
	public String getSALT() {
		return SALT;
	}
	@JsonProperty("MEMB_ID")
	public String getMEMB_ID() {
		return MEMB_ID;
	}
}
