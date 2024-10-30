package com.example.Lee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaltResponseModel {
    private String RSLT_CD;
    private String salt;

    public SaltResponseModel(String RSLT_CD, String salt) {
        this.RSLT_CD = RSLT_CD;
        this.salt = salt;
    }

    public String getRSLT_CD() {
        return RSLT_CD;
    }

    public void setRSLT_CD(String RSLT_CD) {
        this.RSLT_CD = RSLT_CD;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
