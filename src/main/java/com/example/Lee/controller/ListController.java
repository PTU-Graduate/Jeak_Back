package com.example.Lee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.Lee.model.NoticeModel;
import com.example.Lee.service.NoticeListService;
import com.example.Lee.model.CommonResponseModel;

import java.util.List;
import java.util.Map;

@RestController
public class ListController {

    private final NoticeListService noticeService;

    @Autowired
    public ListController(NoticeListService noticeService) {
        this.noticeService = noticeService;
    }

    //공지사항 추가 엔드포인트
    @PostMapping("/PTU/Notice/add")
    public ResponseEntity<CommonResponseModel> createNotice(@RequestBody Map<String, String> requestData) {
        String membId = requestData.get("MEMB_ID");
        String title = requestData.get("TIT");
        String content = requestData.get("CONT");

        if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body(new CommonResponseModel("01"));
        }

        NoticeModel notice = new NoticeModel();
        notice.setMembId(membId);
        notice.setTitle(title);
        notice.setContent(content);

        noticeService.saveNotice(notice);

        return ResponseEntity.ok(new CommonResponseModel("00"));
    }
    
    //공지사항 업데이트 엔드포인트
    @PostMapping("/PTU/Notice/update")
    public ResponseEntity<CommonResponseModel> updateNotice(@RequestBody Map<String, String> requestData) {
    	int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
    	String title = requestData.get("TIT");
    	String content = requestData.get("CONT");
    	
    	CommonResponseModel response = noticeService.updateNotice(creSeq, title, content);
    	return ResponseEntity.ok(response);
    }
    
    @PostMapping("/PTU/Notice/delete")
    public ResponseEntity<CommonResponseModel> deleteNotice(@RequestBody Map<String, String> requestData) {
    	int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
    	
    	CommonResponseModel response = noticeService.deleteNotice(creSeq);
    	return ResponseEntity.ok(response);
    	
    }
    
}
