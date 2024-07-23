package com.example.Lee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.Lee.model.NoticeModel;
import com.example.Lee.service.NoticeListService;

import java.util.Map;

@RestController
public class ListController {

    private final NoticeListService noticeService;

    @Autowired
    public ListController(NoticeListService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/PTU/Notice/add")
    public ResponseEntity<String> createNotice(@RequestBody Map<String, String> requestData) {
        String membId = requestData.get("MEMB_ID");
        String title = requestData.get("TIT");
        String content = requestData.get("CONT");

        if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body("Title and Content cannot be empty.");
        }

        NoticeModel notice = new NoticeModel();
        notice.setMembId(membId);
        notice.setTitle(title);
        notice.setContent(content);

        noticeService.saveNotice(notice);

        return ResponseEntity.ok("Notice created successfully.");
    }
}
