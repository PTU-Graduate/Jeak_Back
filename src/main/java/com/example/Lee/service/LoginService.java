package com.example.Lee.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Lee.model.LoginRsltModel;
import com.example.Lee.model.UserModel;
import com.example.Lee.dao.LoginDao;

@Service
public class LoginService {

    private final LoginDao loginDao;
    private final DepartmentService departmentService;

    public LoginService(LoginDao loginDao, DepartmentService departmentService) {
        this.loginDao = loginDao;
        this.departmentService = departmentService;
    }

    public ResponseEntity<LoginRsltModel> authenticateUser(String membId, String pass) {
        // 데이터베이스에서 MEMB_ID에 대한 정보 조회
        UserModel user = loginDao.findByMembId(membId);

        if (user == null) {
            // ID가 데이터베이스에 없을 경우 결과 코드 "02" 반환
            return ResponseEntity.ok(new LoginRsltModel("02"));
        }

        if (!user.getPass().equals(pass)) {
            // 비밀번호가 일치하지 않을 경우 결과 코드 "01" 반환
            return ResponseEntity.ok(new LoginRsltModel("01"));
        }

        // 학과 코드로 학과 이름 조회
        String departmentName = departmentService.getDepartmentNameByCode(user.getStdDepCd());
        return ResponseEntity.ok(new LoginRsltModel(
                "00",
                user.getStdNum(),
                departmentName,  // 학과 이름 반환
                user.getName(),
                user.getCreCon(),
                user.getEmail()
        ));
    }
}
