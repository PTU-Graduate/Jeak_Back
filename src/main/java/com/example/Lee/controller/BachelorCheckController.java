package com.example.Lee.controller;

import com.example.Lee.model.BachelorCheck;
import com.example.Lee.model.CommonResponseModel;
import com.example.Lee.service.BachelorCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<CommonResponseModel> createBachelor(
            @RequestParam("MEMB_ID") String membId,
            @RequestParam("TIT") String title,
            @RequestParam("CONT") String content,
            @RequestParam(value = "IMAGE", required = false) MultipartFile imageFile) {

        if (membId == null || membId.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body(new CommonResponseModel("01"));
        }

        BachelorCheck bachelor = new BachelorCheck();
        bachelor.setMembId(membId);
        bachelor.setTitle(title);
        bachelor.setContent(content);
        
        if (imageFile == null || imageFile.isEmpty()) {
        	bachelor.setImgCd(null);
        }
        CommonResponseModel response = bachelorCheckService.saveBachelor(bachelor, imageFile, membId);
        return ResponseEntity.ok(response);
    }
    
    //학사안내 업데이트 엔드포인트
    @PostMapping("/PTU/Bachelor/update")
    public ResponseEntity<CommonResponseModel> updateBachelor(@RequestBody Map<String, String> requestData) {
    	int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
    	String title = requestData.get("TIT");
    	String content = requestData.get("CONT");
    	
    	CommonResponseModel response = bachelorCheckService.updateBachelor(creSeq, title, content);
    	return ResponseEntity.ok(response);
    }
    
    //학사안내 삭제 엔드포인트
    @PostMapping("/PTU/Bachelor/delete")
    public ResponseEntity<CommonResponseModel> deleteBachelor(@RequestBody Map<String, String> requestData) {
    	int creSeq = Integer.parseInt(requestData.get("CRE_SEQ"));
    	
    	CommonResponseModel response = bachelorCheckService.deleteBachelor(creSeq);
    	return ResponseEntity.ok(response);
    	
    }
    
}
