package com.example.Lee.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.Lee.dao.StuInfoDao;
import com.example.Lee.model.SaltResponseModel;
import com.example.Lee.model.StuInfo;

@Service
public class StuInfoService {

    private final StuInfoDao stuInfoDao;

    public StuInfoService(StuInfoDao stuInfoDao) {
        this.stuInfoDao = stuInfoDao;
    }

    public ResponseEntity<SaltResponseModel> getSaltByMembId(String membId) {
        if (membId == null || membId.isEmpty()) {
            return ResponseEntity.ok(new SaltResponseModel("99", null));
        }

        try {
            StuInfo stuInfo = stuInfoDao.findById(membId).orElse(null);

            if (stuInfo == null) {
                return ResponseEntity.ok(new SaltResponseModel("01", null));
            }

            return ResponseEntity.ok(new SaltResponseModel("00", stuInfo.getSalt()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new SaltResponseModel("99", null));
        }
    }
}
