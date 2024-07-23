// Department.java
package com.example.Lee.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dep_cd")
public class DepartMentCodeModel {
    @Id
    private String stdDepCd;
    private String membDep;

    // Constructors, getters, and setters
    public DepartMentCodeModel() {}

    public DepartMentCodeModel(String stdDepCd, String membDep) {
        this.stdDepCd = stdDepCd;
        this.membDep = membDep;
    }

    public String getStdDepCd() {
        return stdDepCd;
    }

    public void setStdDepCd(String stdDepCd) {
        this.stdDepCd = stdDepCd;
    }

    public String getMembDep() {
        return membDep;
    }

    public void setMembDep(String membDep) {
        this.membDep = membDep;
    }
}
