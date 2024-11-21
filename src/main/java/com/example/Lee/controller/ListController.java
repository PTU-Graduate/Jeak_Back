package com.example.Lee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Lee.model.NoticeModel;
import com.example.Lee.service.NoticeListService;
import com.example.Lee.model.CommonResponseModel;

import java.util.Map;

@RestController
public class ListController {

    private final NoticeListService noticeService;

    @Autowired
    public ListController(NoticeListService noticeService) {
        this.noticeService = noticeService;
    }

    /**
     * 공지사항 등록 엔드포인트
     */
    @PostMapping("/PTU/Notice/add")
    public ResponseEntity<CommonResponseModel> createNotice(
            @RequestBody Map<String, String> requestData) {
        String membId = requestData.get("MEMB_ID");
        String title = requestData.get("TIT");
        String content = requestData.get("CONT");
        String base64Image = requestData.get("IMAGE"); // Base64 인코딩된 이미지

        if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body(new CommonResponseModel("01"));
        }

        NoticeModel notice = new NoticeModel();
        notice.setMembId(membId);
        notice.setTitle(title);
        notice.setContent(content);

        CommonResponseModel response = noticeService.saveNotice(notice, base64Image, membId);
        return ResponseEntity.ok(response);
    }

    /**
     * 공지사항 수정 엔드포인트
     */
    @PostMapping("/PTU/Notice/update")
    public ResponseEntity<CommonResponseModel> updateNotice(
            @RequestBody Map<String, String> requestData) {
        try {
            int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
            String title = requestData.get("TIT");
            String content = requestData.get("CONT");
            String imageInput = requestData.get("IMAGE"); // IMAGE 값

            CommonResponseModel response = noticeService.updateNotice(creSeq, title, content, imageInput);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponseModel("02"));
        }
    }

    /**
     * 공지사항 삭제 엔드포인트
     */
    @PostMapping("/PTU/Notice/delete")
    public ResponseEntity<CommonResponseModel> deleteNotice(@RequestBody Map<String, String> requestData) {
        int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));

        CommonResponseModel response = noticeService.deleteNotice(creSeq);
        return ResponseEntity.ok(response);
    }
}
