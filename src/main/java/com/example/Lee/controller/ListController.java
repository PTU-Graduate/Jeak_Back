package com.example.Lee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping("/PTU/Notice/add")
    public ResponseEntity<CommonResponseModel> createNotice(
            @RequestParam("MEMB_ID") String membId,
            @RequestParam("TIT") String title,
            @RequestParam("CONT") String content,
            @RequestParam(value = "IMAGE", required = false) MultipartFile imageFile) {
        
        if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body(new CommonResponseModel("01"));
        }

        NoticeModel notice = new NoticeModel();
        notice.setMembId(membId);
        notice.setTitle(title);
        notice.setContent(content);

        // 이미지 파일이 null인 경우를 처리
        if (imageFile == null || imageFile.isEmpty()) {
            notice.setImgCd(null);  // 이미지 경로를 null로 설정
        }

        CommonResponseModel response = noticeService.saveNotice(notice, imageFile, membId);
        return ResponseEntity.ok(response);
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
