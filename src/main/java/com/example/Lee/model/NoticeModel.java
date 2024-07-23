package com.example.Lee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "notice_list")
public class NoticeModel {
	
    @Id // 기본 키(PK) 필드임을 나타냄
    private String membId;
    
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    // 기본 생성자: JPA에서 엔티티 클래스는 기본 생성자를 가지고 있어야 함
    public NoticeModel() {
    }

    // 모든 속성을 포함하는 생성자
    public NoticeModel(String membId, String title, String content, LocalDateTime createdDate) {        
    	this.membId = membId;
    	this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    // Getter and Setter 메서드: 엔티티의 속성에 접근하기 위한 메서드
    public String getId() {
        return membId;
    }

    public void setId(String membId) {
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
}
