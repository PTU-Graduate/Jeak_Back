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
@Table(name = "notice_List")
public class ScholarshipModel {
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long creSEQ; // Long 타입으로 설정
	   
	   @Column(name="MEMB_ID")
	   private String membId;
	   
	   @Column(name="TIT")
	   private String title;
	   
	   @Column(name="CONT")
	   private String content;
 
       @Column(name="CRE_DATE")
       private LocalDateTime createDate;
       
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
       
       public String getContent( ) {
    	   return content;
       }
       
       public void setContent(String content) {
    	   this.content = content;
       }
       
       public LocalDateTime getCreateData() {
    	   return createDate;
       }
       
       public void setCreateData(LocalDateTime createData) {
    	   this.createDate = createData;
       }
       
       
       @PrePersist
       protected void onCreate() {
    	   this.createDate = LocalDateTime.now();      
    	   }
       }