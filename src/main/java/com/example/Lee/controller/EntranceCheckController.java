package com.example.Lee.controller;

import com.example.Lee.model.EntranceCheck;
import com.example.Lee.service.EntranceCheckService;
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
public class EntranceCheckController {
    private final EntranceCheckService entranceCheckService;

    @Autowired
    public EntranceCheckController(EntranceCheckService entranceCheckService) {
        this.entranceCheckService = entranceCheckService;
    }

    @PostMapping("/entrance")
    public Map<String, Object> getEntrance(@RequestParam(defaultValue = "0") int page) {
        Map<String, Object> response = new HashMap<>();
        try {
            int size = 10; // 페이지 당 게시글 수
            Page<EntranceCheck> scholarPage = entranceCheckService.getEntrance(page, size);

            response.put("RSLT_CD", "00"); // 호출 정상
            response.put("ENTRANCE", scholarPage.getContent());
        } catch (Exception e) {
            response.put("RSLT_CD", "99"); // 호출 실패
            response.put("error", e.getMessage());
        }
        return response;
    }
    
    //입학안내 추가 엔드포인트
    @PostMapping("/PTU/Entrance/add")
    public ResponseEntity<String> createEntrance(@RequestBody Map<String, String> requestData) {
        String membId = requestData.get("MEMB_ID");
        String title = requestData.get("TIT");
        String content = requestData.get("CONT");

        if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body("Title and Content cannot be empty.");
        }

        EntranceCheck entrance = new EntranceCheck();
        entrance.setMembId(membId);
        entrance.setTitle(title);
        entrance.setContent(content);

        entranceCheckService.saveEntrance(entrance);

        return ResponseEntity.ok("Entrance created successfully.");
    }
}
