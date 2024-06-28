package com.example.Lee.service;

import org.springframework.beans.factory.annotation.Autowired; // 스프링의 의존성 주입을 위한 어노테이션
import org.springframework.stereotype.Service; // 스프링에서 서비스 계층의 컴포넌트를 정의하는 어노테이션
import org.springframework.transaction.annotation.Propagation; //스프링에서 롤백의 범위를 지정하는 어노테이션
import org.springframework.transaction.annotation.Transactional;

import com.example.Lee.dao.RegiRepositoryDao; // 회원 정보에 접근하기 위한 DAO
import com.example.Lee.model.CommonResponseModel; // 클라이언트에 반환될 공통 응답 모델
import com.example.Lee.model.RegiModel; // 등록할 회원의 정보 모델

@Service // 이 클래스가 서비스 계층의 컴포넌트임을 나타냄
public class IdRegiService {

	private final RegiRepositoryDao regiRepository; // 회원 정보에 접근하기 위한 레포지토리 객체

	@Autowired // 스프링이 자동으로 해당 타입의 빈(Bean)을 주입
	public IdRegiService(RegiRepositoryDao regiRepository) {
		this.regiRepository = regiRepository; // 생성자를 통해 주입받은 레포지토리 객체를 필드에 할당
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public CommonResponseModel registerId(RegiModel regiData) {
		// 사용자 ID가 이미 등록되어 있는지 확인
		if (regiRepository.existsByMembId(regiData.getMembId())) {
			return new CommonResponseModel("01"); // ID가 중복인 경우 응답 코드 "01" 반환
		}
		// ID 등록 성공 시 응답 코드 "00" 반환
		return new CommonResponseModel("00");
	}
	

}