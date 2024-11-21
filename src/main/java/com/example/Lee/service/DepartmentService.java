package com.example.Lee.service;

import com.example.Lee.model.DepartMentCodeModel;
import com.example.Lee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // 학과 이름을 통해 학과 코드 조회
    public String getDepartmentCodeByName(String membDep) {
        return departmentRepository.findByMembDep(membDep)
                .map(DepartMentCodeModel::getStdDepCd)
                .orElse("Unknown");
    }

    // 학과 코드로 학과 이름 조회
    public String getDepartmentNameByCode(String stdDepCd) {
        return departmentRepository.findById(stdDepCd)
                .map(DepartMentCodeModel::getMembDep)
                .orElse("Unknown");
    }
}
