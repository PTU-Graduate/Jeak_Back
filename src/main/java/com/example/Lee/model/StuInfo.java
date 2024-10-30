package com.example.Lee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="stu_info")
public class StuInfo {
    @Id
    private String membId;
    private String salt;

    // 기본 생성자
    public StuInfo() {}

    // 생성자
    public StuInfo(String membId, String salt) {
        this.membId = membId;
        this.salt = salt;
    }

    // Getter 및 Setter
    public String getMembId() {
        return membId;
    }

    public void setMembId(String membId) {
        this.membId = membId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
