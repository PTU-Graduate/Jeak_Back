package com.example.Lee.controller;

import com.example.Lee.model.ScholarCheck;
import com.example.Lee.service.ScholarCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ScholarCheckController {
    private final ScholarCheckService scholarCheckService;

    @Autowired
    public ScholarCheckController(ScholarCheckService scholarCheckService) {
        this.scholarCheckService = scholarCheckService;
    }

    @PostMapping("/scholar")
    public Map<String, Object> getScholar(@RequestParam(defaultValue = "0") int page) {
        Map<String, Object> response = new HashMap<>();
        try {
            int size = 10; // 페이지 당 게시글 수
            Page<ScholarCheck> scholarPage = scholarCheckService.getScholar(page, size);

            response.put("RSLT_CD", "00"); // 호출 정상
            response.put("SCHOLAR", scholarPage.getContent());
        } catch (Exception e) {
            response.put("RSLT_CD", "99"); // 호출 실패
            response.put("error", e.getMessage());
        }
        return response;
    }
    
    //장학안내 추가 엔드포인트
    @PostMapping("/PTU/Scholar/add")
    public ResponseEntity<String> createScholar(@RequestBody Map<String, String> requestData) {
        String membId = requestData.get("MEMB_ID");
        String title = requestData.get("TIT");
        String content = requestData.get("CONT");

        if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body("Title and Content cannot be empty.");
        }

        ScholarCheck scholar = new ScholarCheck();
        scholar.setMembId(membId);
        scholar.setTitle(title);
        scholar.setContent(content);

        scholarCheckService.saveScholar(scholar);

        return ResponseEntity.ok("Scholar created successfully.");
    }
    
}
