package com.example.Lee.controller;

import com.example.Lee.model.BachelorCheck;
import com.example.Lee.service.BachelorCheckService;
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
public class BachelorCheckController {
    private final BachelorCheckService bachelorCheckService;

    @Autowired
    public BachelorCheckController(BachelorCheckService bachelorCheckService) {
        this.bachelorCheckService = bachelorCheckService;
    }

    @PostMapping("/bachelor")
    public Map<String, Object> getBachelor(@RequestParam(defaultValue = "0") int page) {
        Map<String, Object> response = new HashMap<>();
        try {
            int size = 10; // 페이지 당 게시글 수
            Page<BachelorCheck> bachelorPage = bachelorCheckService.getBachelor(page, size);

            response.put("RSLT_CD", "00"); // 호출 정상
            response.put("BACHELOR", bachelorPage.getContent());
        } catch (Exception e) {
            response.put("RSLT_CD", "99"); // 호출 실패
            response.put("error", e.getMessage());
        }
        return response;
    }
    
    // 학사안내 추가 엔드포인트
    @PostMapping("/PTU/Bachelor/add")
    public ResponseEntity<String> createBachelor(@RequestBody Map<String, String> requestData) {
        String membId = requestData.get("MEMB_ID");
        String title = requestData.get("TIT");
        String content = requestData.get("CONT");

        if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body("Title and Content cannot be empty.");
        }

        BachelorCheck bachelor = new BachelorCheck();
        bachelor.setMembId(membId);
        bachelor.setTitle(title);
        bachelor.setContent(content);

        bachelorCheckService.saveBachelor(bachelor);

        return ResponseEntity.ok("Bachelor created successfully.");
    }
    
}
