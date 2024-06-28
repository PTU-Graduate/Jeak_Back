package com.example.Lee.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired; // 스프링의 의존성 주입을 위한 어노테이션
import org.springframework.http.ResponseEntity; // HTTP 응답을 나타내는 클래스
import org.springframework.web.bind.annotation.PostMapping; // HTTP POST 요청을 처리하는 어노테이션
import org.springframework.web.bind.annotation.RequestBody; // HTTP 요청의 본문을 파싱하는 어노테이션
import org.springframework.web.bind.annotation.RestController; // RESTful 컨트롤러임을 나타내는 어노테이션

import com.example.Lee.model.LoginRsltModel; // 로그인 결과를 담는 모델 클래스
import com.example.Lee.service.LoginService; // 로그인 관련 비즈니스 로직을 처리하는 서비스 클래스

@RestController // 이 클래스가 RESTful 컨트롤러임을 나타내는 어노테이션
public class LoginController {

	private final LoginService loginService; // LoginService 의존성 주입을 위한 필드

	@Autowired // 의존성 주입을 위한 어노테이션
	public LoginController(LoginService loginService) { // 생성자 주입을 통해 LoginService 주입
		this.loginService = loginService; // 주입받은 LoginService를 필드에 할당
	}

	@PostMapping("/PTU/Login") // HTTP POST 요청을 처리하는 메소드를 나타내는 어노테이션과 해당 엔드포인트 경로
	public ResponseEntity<LoginRsltModel> login(@RequestBody Map<String, String> requestData) { // HTTP 요청의 본문을 Map으로
																								// 파싱하여 받아오는 메소드
		String loginId = requestData.get("MEMB_ID"); // 요청에서 "MEMB_ID" 키의 값을 가져옴
		String loginPass = requestData.get("PASS"); // 요청에서 "PASS" 키의 값을 가져옴

		if (loginId == null || loginId.isEmpty() || loginPass == null || loginPass.isEmpty()) { // 로그인 아이디나 비밀번호가 비어있는
																								// 경우
			throw new IllegalArgumentException("LOGIN_ID와 LOGIN_PASS를 제대로 입력하세요."); // 예외를 던져 예외 메시지를 반환
		}

		// 패스워드가 비어있는 경우 IllegalArgumentException을 발생시킴
		if (loginPass.isEmpty() || loginId.isEmpty()) { // 로그인 아이디나 비밀번호가 비어있는 경우
			throw new IllegalArgumentException("LOGIN_PASS를 제대로 입력하세요."); // 예외를 던져 예외 메시지를 반환
		}

		// authenticateUser 메서드 호출 및 반환값 그대로 반환
		return loginService.authenticateUser(loginId, loginPass); // LoginService를 통해 사용자 인증을 수행하고 그 결과를 반환
	}
}
