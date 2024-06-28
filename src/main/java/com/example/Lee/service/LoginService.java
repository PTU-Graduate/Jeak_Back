package com.example.Lee.service;

import org.springframework.http.ResponseEntity; // HTTP 응답을 처리하기 위한 클래스
import org.springframework.stereotype.Service; // 스프링에서 서비스 계층을 정의하는 어노테이션

import com.example.Lee.dao.LoginDao; // 사용자 정보를 데이터베이스에서 가져오는 DAO
import com.example.Lee.model.LoginRsltModel; // 로그인 결과를 모델링하는 클래스
import com.example.Lee.model.UserModel; // 사용자 정보를 모델링하는 클래스

@Service // 이 클래스가 서비스 계층의 컴포넌트임을 나타내는 어노테이션
public class LoginService {

	private final LoginDao loginDao; // 데이터 액세스 객체(DAO)의 의존성을 주입받는 필드

	public LoginService(LoginDao loginDao) { // 생성자를 통해 DAO 주입
		this.loginDao = loginDao; // 주입받은 DAO를 필드에 저장
	}

	public ResponseEntity<LoginRsltModel> authenticateUser(String membId, String pass) {
		// 데이터베이스에서 해당 MEMB_ID에 대한 정보 조회
		UserModel user = loginDao.findByMembId(membId);

		// MEMB_ID가 존재하지 않는 경우
		if (user == null) {
			// ID가 데이터베이스에 없음을 나타내는 결과 코드 "02"를 가진 모델을 반환
			return ResponseEntity.ok(new LoginRsltModel("02"));
		}

		// MEMB_ID에 해당하는 PASS와 비교하여 인증 수행
		if (!user.getPass().equals(pass)) {
			// 비밀번호가 일치하지 않을 경우 결과 코드 "01"을 가진 모델을 반환
			return ResponseEntity.ok(new LoginRsltModel("01"));
		}

		// 인증 성공
		// 사용자의 학번, 학과 코드, 이름, 등급 등을 포함한 성공 결과 모델을 반환
		return ResponseEntity
				.ok(new LoginRsltModel("00", user.getStdNum(), user.getStdDepCd(), user.getName()));
	}
}
