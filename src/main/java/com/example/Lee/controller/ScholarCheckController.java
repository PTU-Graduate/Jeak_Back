package com.example.Lee.controller;

import com.example.Lee.model.CommonResponseModel;
import com.example.Lee.model.ScholarCheck;
import com.example.Lee.service.ScholarCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public Map<String, Object> getScholar(@RequestBody Map<String, String> requestData)  {
    	int page = Integer.parseInt(requestData.get("page"));
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

    // 장학안내 등록 엔드포인트
    @PostMapping("/PTU/Scholar/add")
    public ResponseEntity<CommonResponseModel> createScholar(
            @RequestBody Map<String, String> requestData) {
        try {
            String membId = requestData.get("MEMB_ID");
            String title = requestData.get("TIT");
            String content = requestData.get("CONT");
            String base64Image = requestData.get("IMAGE"); // Base64 인코딩된 이미지

            if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
                return ResponseEntity.badRequest().body(new CommonResponseModel("01"));
            }

            ScholarCheck scholar = new ScholarCheck();
            scholar.setMembId(membId);
            scholar.setTitle(title);
            scholar.setContent(content);

            CommonResponseModel response = scholarCheckService.saveScholar(scholar, base64Image, membId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponseModel("02")); // 이미지 처리 실패 시 02 반환
        }
    }

    // 장학안내 수정 엔드포인트
    @PostMapping("/PTU/Scholar/update")
    public ResponseEntity<CommonResponseModel> updateScholar(@RequestBody Map<String, String> requestData) {
        try {
            int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
            String title = requestData.get("TIT");
            String content = requestData.get("CONT");
            String imageInput = requestData.get("IMAGE"); // Base64 이미지 입력

            CommonResponseModel response = scholarCheckService.updateScholar(creSeq, title, content, imageInput);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponseModel("02")); // 이미지 처리 실패 시 02 반환
        }
    }

    // 장학안내 삭제 엔드포인트
    @PostMapping("/PTU/Scholar/delete")
    public ResponseEntity<CommonResponseModel> deleteScholar(@RequestBody Map<String, String> requestData) {
        try {
            int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
            CommonResponseModel response = scholarCheckService.deleteScholar(creSeq);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponseModel("02")); // 처리 실패 시 02 반환
        }
    }
}
