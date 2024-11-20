package com.example.Lee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Lee.model.NoticeModel;
import com.example.Lee.service.NoticeListService;
import com.example.Lee.model.CommonResponseModel;

import java.io.IOException;
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


    
    @PostMapping("/PTU/Notice/update")
    public ResponseEntity<CommonResponseModel> updateNotice(
            @RequestPart("data") Map<String, String> requestData,
            @RequestPart(value = "IMAGE", required = false) MultipartFile imageFile) {
        try {
            int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
            String title = requestData.get("TIT");
            String content = requestData.get("CONT");
            String imageUrl = requestData.get("IMAGE"); // 문자열로 전송된 기존 URL

            CommonResponseModel response = noticeService.updateNotice(creSeq, title, content, imageFile, imageUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new CommonResponseModel("02"));
        }
    }




    
    @PostMapping("/PTU/Notice/delete")
    public ResponseEntity<CommonResponseModel> deleteNotice(@RequestBody Map<String, String> requestData) {
    	int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
    	
    	CommonResponseModel response = noticeService.deleteNotice(creSeq);
    	return ResponseEntity.ok(response);
    	
    }
    
}
