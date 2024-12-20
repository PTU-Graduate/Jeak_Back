package com.example.Lee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice_list")
public class NoticeCheck {
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
    
    @Column(name = "IMG_CD")
    private String img;

    @Column(name = "CRE_DATE")
    private LocalDateTime creDate;

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
    
    @JsonProperty("IMG_CD")
    public String getImgCd() {
        return img;
    }

    public void setImgCd(String img) {
        this.img = img;
    }
    @JsonProperty("CRE_DATE")
    public LocalDateTime getCreDate() {
        return creDate;
    }

    public void setCreDate(LocalDateTime creDate) {
        this.creDate = creDate;
    }
}
