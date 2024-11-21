package com.example.Lee.controller;

import com.example.Lee.model.BachelorCheck;
import com.example.Lee.model.CommonResponseModel;
import com.example.Lee.service.BachelorCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BachelorCheckController {

    private final BachelorCheckService bachelorCheckService;

    @Autowired
    public BachelorCheckController(BachelorCheckService bachelorCheckService) {
        this.bachelorCheckService = bachelorCheckService;
    }

    /**
     * 학사안내 조회 엔드포인트
     */
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

    /**
     * 학사안내 등록 엔드포인트
     */
    @PostMapping("/PTU/Bachelor/add")
    public ResponseEntity<CommonResponseModel> createBachelor(@RequestBody Map<String, String> requestData) {
        String membId = requestData.get("MEMB_ID");
        String title = requestData.get("TIT");
        String content = requestData.get("CONT");
        String base64Image = requestData.get("IMAGE"); // Base64 인코딩된 이미지

        if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body(new CommonResponseModel("01"));
        }

        BachelorCheck bachelor = new BachelorCheck();
        bachelor.setMembId(membId);
        bachelor.setTitle(title);
        bachelor.setContent(content);

        CommonResponseModel response = bachelorCheckService.saveBachelor(bachelor, base64Image, membId);
        return ResponseEntity.ok(response);
    }

    /**
     * 학사안내 수정 엔드포인트
     */
    @PostMapping("/PTU/Bachelor/update")
    public ResponseEntity<CommonResponseModel> updateBachelor(@RequestBody Map<String, String> requestData) {
        try {
            int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
            String title = requestData.get("TIT");
            String content = requestData.get("CONT");
            String imageInput = requestData.get("IMAGE"); // IMAGE 값

            CommonResponseModel response = bachelorCheckService.updateBachelor(creSeq, title, content, imageInput);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponseModel("02"));
        }
    }

    /**
     * 학사안내 삭제 엔드포인트
     */
    @PostMapping("/PTU/Bachelor/delete")
    public ResponseEntity<CommonResponseModel> deleteBachelor(@RequestBody Map<String, String> requestData) {
        int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));

        CommonResponseModel response = bachelorCheckService.deleteBachelor(creSeq);
        return ResponseEntity.ok(response);
    }
}
