package com.example.Lee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice_list")
public class NoticeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRE_SEQ")
    private Long creSEQ; // Long 타입으로 설정
    
    @Column(name="MEMB_ID")
    private String membId;

    @Column(name="TIT")
    private String title;

    @Column(name="CONT")
    private String content;

    @Column(name="CRE_DATE")
    private LocalDateTime createdDate;

    @Column(name="IMG_CD")  // DB의 이미지 경로 컬럼명에 맞게 변경
    private String imgCd;  // 이미지 경로를 저장할 필드명

    // Getter and Setter
    public Long getSeq() {
        return creSEQ;
    }

    public void setSeq(Long creSEQ) {
        this.creSEQ = creSEQ;
    }

    public String getMembId() {
        return membId;
    }

    public void setMembId(String membId) {
        this.membId = membId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getImgCd() {  // 이미지 경로에 대한 getter
        return imgCd;
    }

    public void setImgCd(String imgCd) {  // 이미지 경로에 대한 setter
        this.imgCd = imgCd;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}
