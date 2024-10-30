package com.example.Lee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.Lee.service.StuInfoService;
import com.example.Lee.model.SaltResponseModel;

import java.util.Map;

@RestController
public class StuInfoController {

    private final StuInfoService stuInfoService;

    @Autowired
    public StuInfoController(StuInfoService stuInfoService) {
        this.stuInfoService = stuInfoService;
    }

    @PostMapping("/getSalt")
    public ResponseEntity<SaltResponseModel> getSalt(@RequestBody Map<String, String> requestData) {
        String membId = requestData.get("MEMB_ID");

        if (membId == null || membId.isEmpty()) {
            return ResponseEntity.ok(new SaltResponseModel("99", null));
        }

        return stuInfoService.getSaltByMembId(membId);
    }
}
