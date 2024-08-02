package com.example.Lee.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Lee.dao.ScholarshipDao;
import com.example.Lee.model.ScholarshipModel;


@Service
public class SchorolshipService {
	
	@Autowired
	private ScholarshipDao  schorolshipDao;
	
	@Transactional
	public ScholarshipModel saveScholarship(ScholarshipModel scholarship) {
		if (scholarship.getTitle() == null || scholarship.getContenet() == null) {
			 throw new IllegalArgumentException("Title and Content cannot be null");
			 
		}
		return schorolshipDao.save(scholarship);
	}

}
