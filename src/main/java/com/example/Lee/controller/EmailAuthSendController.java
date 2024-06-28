package com.example.Lee.controller;

import java.util.Map; // Map을 사용하여 요청 데이터를 받음

import org.springframework.beans.factory.annotation.Autowired; // Spring의 의존성 주입을 사용
import org.springframework.web.bind.annotation.PostMapping; // POST 요청을 처리하기 위한 어노테이션
import org.springframework.web.bind.annotation.RequestBody; // 요청 본문을 매핑하기 위한 어노테이션
import org.springframework.web.bind.annotation.RequestMapping; // 요청 URL을 매핑하기 위한 어노테이션
import org.springframework.web.bind.annotation.RestController; // RESTful 웹 서비스의 컨트롤러임을 선언

import com.example.Lee.model.CommonResponseModel; // 공통 응답 모델 클래스
import com.example.Lee.service.EmailAuthService; // 이메일 인증 서비스 클래스

@RestController // 이 클래스가 REST 컨트롤러임을 선언
@RequestMapping("/PTU/Auth") // "/PTU/Auth" 경로로 들어오는 요청을 이 컨트롤러에서 처리함
public class EmailAuthSendController {

	@Autowired // EmailAuthService의 인스턴스를 자동 주입
	private EmailAuthService emailAuthService;

	// 인증 코드를 전송하는 엔드포인트
	@PostMapping("/CodeSend") // "/CodeSend" 경로로 들어오는 POST 요청을 처리함
	public CommonResponseModel sendVerificationCode(@RequestBody Map<String, String> request) {
		String membId = request.get("MEMB_ID"); // 요청 본문에서 MEMB_ID를 추출
		return emailAuthService.sendVerificationCode(membId); // 인증 코드 전송 서비스 호출
	}

	// 인증 코드를 검증하는 엔드포인트
	@PostMapping("/VerifyCode") // "/VerifyCode" 경로로 들어오는 POST 요청을 처리함
	public CommonResponseModel verifyCode(@RequestBody Map<String, String> request) {
		String membId = request.get("MEMB_ID"); // 요청 본문에서 MEMB_ID를 추출
		String code = request.get("CRE_CODE"); // 요청 본문에서 CRE_CODE를 추출
		return emailAuthService.verifyCode(membId, code); // 인증 코드 검증 서비스 호출

	}
}
