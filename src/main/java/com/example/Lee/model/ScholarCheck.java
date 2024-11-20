package com.example.Lee.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import jakarta.persistence.PrePersist;

@Entity
@Table(name = "scholar_list")
public class ScholarCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRE_SEQ")
    private Long creSeq;

    @Column(name = "MEMB_ID")
    private String membId;

    @Column(name = "TIT")
    private String title;

    @Column(name = "CONT")
    private String content;

    @Column(name = "CRE_DATE")
    private LocalDateTime creDate;
    
    @Column(name = "IMG_CD")
    private String imgCd;

    // Getters and Setters
    @JsonProperty("CRE_SEQ")
    public Long getCreSeq() {
        return creSeq;
    }

    public void setCreSeq(Long creSeq) {
        this.creSeq = creSeq;
    }
    @JsonProperty("MEMB_ID")
    public String getMembId() {
        return membId;
    }

    public void setMembId(String membId) {
        this.membId = membId;
    }
    @JsonProperty("TIT")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @JsonProperty("CONT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @JsonProperty("CRE_DATE")
    public LocalDateTime getCreDate() {
        return creDate;
    }

    public void setCreDate(LocalDateTime creDate) {
        this.creDate = creDate;
    }
    
    public String getImgCd() {
    	return imgCd;
    }
    
    public void setImgCd(String imgCd) {
    	this.imgCd = imgCd;
    }
    
    @PrePersist
    protected void onCreate() {
        this.creDate = LocalDateTime.now();
    }
}
