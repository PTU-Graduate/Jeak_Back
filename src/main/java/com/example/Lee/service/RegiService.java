package com.example.Lee.service;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired; // 스프링의 의존성 주입을 위한 어노테이션
import org.springframework.stereotype.Service; // 스프링에서 서비스 계층의 컴포넌트를 정의하는 어노테이션

import com.example.Lee.dao.RegiRepositoryDao; // 회원 정보에 접근하기 위한 DAO
import com.example.Lee.model.CommonResponseModel; // 클라이언트에 반환될 공통 응답 모델
import com.example.Lee.model.BasicUserDataSave;
import com.example.Lee.model.RegiModel; // 등록할 회원의 정보 모델

@Service // 이 클래스가 서비스 계층의 컴포넌트임을 나타냄
public class RegiService {

	private final RegiRepositoryDao regiRepository; // 회원 정보에 접근하기 위한 레포지토리 객체
	private static final Random random = new SecureRandom();

	@Autowired // 스프링이 자동으로 해당 타입의 빈(Bean)을 주입
	public RegiService(RegiRepositoryDao regiRepository) {
		this.regiRepository = regiRepository; // 생성자를 통해 주입받은 레포지토리 객체를 필드에 할당
	}
	public BasicUserDataSave basicRegiUserData(RegiModel regiData) {
		if (regiRepository.existsByMembId(regiData.getMembId())) {
            return new BasicUserDataSave("01",null,null); // ID가 중복인 경우 응답 코드 "01" 반환
        }
		byte[] saltBytes = new byte[16];
		random.nextBytes(saltBytes);
		 String salt = Base64.getEncoder().encodeToString(saltBytes);
	     regiData.setSalt(salt);
	     
	     regiRepository.save(regiData);
	     
	     BasicUserDataSave response = new BasicUserDataSave();
	        response.setRSLT_CD("00");
	        response.setSALT(salt);
	        response.setMEMB_ID(regiData.getMembId());
	        return response;
	}
    public CommonResponseModel completeRegistration(RegiModel regiData) {
        // 기본 정보가 존재하는지 확인
        RegiModel existingData = regiRepository.findByMembId(regiData.getMembId());
        if (existingData == null) {
        	return new CommonResponseModel("03"); // 기본 정보가 존재하지 않는 경우 응답 코드 "03" 반환
        }

        // 추가 정보를 업데이트
        existingData.setEmail(regiData.getEmail());
        existingData.setPass(regiData.getPass());
        existingData.setStdDepCd(regiData.getStdDepCd());
        existingData.setName(regiData.getName());

        // 최종 정보 업데이트
        regiRepository.save(existingData);

        return new CommonResponseModel("00"); // 성공 시 응답 코드 "00" 반환
    }

}
