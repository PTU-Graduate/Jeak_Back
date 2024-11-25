package com.example.Lee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRsltModel {
    private String RSLT_CD;
    private String STD_NUM;
    private String NAME;
    private String EMAIL;
    private String CRE_CON;
    private String DEPARTMENT_NAME; // 학과 이름 필드 추가

    // 결과 코드만 반환할 때 사용하는 생성자
    public LoginRsltModel(String RSLT_CD) {
        this.RSLT_CD = RSLT_CD;
    }

    // 모든 필드를 포함하는 생성자
    public LoginRsltModel(String RSLT_CD, String STD_NUM,  String NAME, String EMAIL, String CRE_CON, String DEPARTMENT_NAME) {
        this.RSLT_CD = RSLT_CD;
        this.STD_NUM = STD_NUM;
        this.NAME = NAME;
        this.EMAIL = EMAIL;
        this.CRE_CON = CRE_CON;
        this.DEPARTMENT_NAME = DEPARTMENT_NAME;
    }

    @JsonProperty("RSLT_CD")
    public String getRSLT_CD() {
        return RSLT_CD;
    }

    @JsonProperty("STD_NUM")
    public String getSTD_NUM() {
        return STD_NUM;
    }

    @JsonProperty("NAME")
    public String getNAME() {
        return NAME;
    }

    @JsonProperty("EMAIL")
    public String getEMAIL() {
        return EMAIL;
    }

    @JsonProperty("CRE_CON")
    public String getCRE_CON() {
        return CRE_CON;
    }

    @JsonProperty("DEPARTMENT_NAME")
    public String getDEPARTMENT_NAME() {
        return DEPARTMENT_NAME;
    }
}
