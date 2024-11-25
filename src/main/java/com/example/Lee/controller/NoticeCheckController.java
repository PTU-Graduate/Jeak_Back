package com.example.Lee.controller;

import com.example.Lee.model.NoticeCheck;
import com.example.Lee.service.NoticeCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class NoticeCheckController {
    private final NoticeCheckService noticeCheckService;

    @Autowired
    public NoticeCheckController(NoticeCheckService noticeCheckService) {
        this.noticeCheckService = noticeCheckService;
    }

    @PostMapping("/notices")
    public Map<String, Object> getNotices(@RequestBody Map<String, String> requestData)  {
    	int page = Integer.parseInt(requestData.get("page"));
        Map<String, Object> response = new HashMap<>();
        try {
            int size = 10; // 페이지 당 게시글 수
            Page<NoticeCheck> noticePage = noticeCheckService.getNotices(page, size);

            response.put("RSLT_CD", "00"); // 호출 정상
            response.put("NOTICES", noticePage.getContent());
        } catch (Exception e) {
            response.put("RSLT_CD", "99"); // 호출 실패
            response.put("error", e.getMessage());
        }
        return response;
    }
    
}
