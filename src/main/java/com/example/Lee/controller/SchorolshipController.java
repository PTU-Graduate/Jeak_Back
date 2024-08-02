package com.example.Lee.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.Lee.model.ScholarshipModel;
import com.example.Lee.service.SchorolshipService;

import java.util.Map;

@RestController
public class SchorolshipController {

	  private final SchorolshipService schorolshipService;
	  
	  @Autowired
	  public SchorolshipController(SchorolshipService schorolshipService) {
		  this.schorolshipService = schorolshipService;
		  
	  }
	  
	  @PostMapping("/PTU/Schorolship/add")
	  public ResponseEntity<String> createNotice(@RequestBody Map<String,String> requestData) {
		  String membId = requestData.get("MEMB_ID");
		  String title = requestData.get("TIT");
		  String content = requestData.get("CONT");
		  
		  if (membId == null || membId.isEmpty()  || title == null || title.isEmpty()  ||  content  ==  null  ||  content.isEmpty()) {
			  return ResponseEntity.badRequest().body("Title and Content cannot be empty.");
			  
		  }
		  
		  ScholarshipModel scholarship = new ScholarshipModel();
		  scholarship.setMembId(membId);
		  scholarship.setTitle(title);
		  scholarship.setContent(content);
		  
		  schorolshipService.saveScholarship(scholarship);
		  
		  return ResponseEntity.ok("Notice created succcessfully.");
	  }
}

