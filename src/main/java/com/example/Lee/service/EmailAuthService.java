package com.example.Lee.service;

import java.time.LocalDateTime; // LocalDateTime을 사용하여 시간 관리
import java.util.Optional; // Optional 클래스를 사용하여 null을 안전하게 처리
import java.util.Random; // Random 클래스를 사용하여 랜덤 코드 생성

import org.springframework.beans.factory.annotation.Autowired; // Spring의 의존성 주입을 사용
import org.springframework.mail.MailException; // 메일 예외 처리를 위한 클래스
import org.springframework.mail.SimpleMailMessage; // 간단한 메일 메시지 클래스를 사용
import org.springframework.mail.javamail.JavaMailSender; // 메일 발송을 위한 JavaMailSender 사용
import org.springframework.stereotype.Service; // 이 클래스가 서비스 클래스임을 명시
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 관리를 위한 어노테이션

import com.example.Lee.model.CommonResponseModel; // 공통 응답 모델 클래스
import com.example.Lee.model.EmailAuthModel; // 이메일 인증 모델 클래스
import com.example.Lee.repository.EmailAuthRepository; // 이메일 인증 레포지토리 인터페이스

@Service // 이 클래스가 서비스 레이어의 빈임을 선언
public class EmailAuthService {

	@Autowired // JavaMailSender의 인스턴스를 자동 주입
	private JavaMailSender javaMailSender;

	@Autowired // EmailAuthRepository의 인스턴스를 자동 주입
	private EmailAuthRepository userRepository;

	private static final long CODE_EXPIRATION_TIME = 3; // 인증 코드 유효 시간을 3분으로 설정

	// 이메일로 인증 코드를 발송하는 메서드
	public void sendVerificationEmail(String toEmail, String code) throws MailException {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail); // 수신자 이메일 주소 설정
		message.setSubject("평택대학교 이메일 인증"); // 이메일 제목 설정
		message.setText("인증 코드를 앱에 입력해 주세요 : " + code); // 이메일 내용 설정
		javaMailSender.send(message); // 이메일 발송
	}

	// 인증 코드를 생성하고 이메일로 발송하는 메서드
	@Transactional // 트랜잭션 관리를 위해 어노테이션 추가
	public CommonResponseModel sendVerificationCode(String membId) {
		Optional<EmailAuthModel> optionalUser = userRepository.findByMembId(membId); // 회원 ID로 사용자 조회
		if (!optionalUser.isPresent()) {
			return new CommonResponseModel("01"); // 사용자가 존재하지 않으면 "01" 반환
		}

		EmailAuthModel user = optionalUser.get(); // 사용자 정보 가져오기
		String code = generateVerificationCode(); // 인증 코드 생성

		try {
			sendVerificationEmail(user.getEmail(), code); // 이메일로 인증 코드 발송
			user.setCreCode(code); // 사용자 정보에 인증 코드 설정
			user.setCreCodeTime(LocalDateTime.now()); // 인증 코드 생성 시간 설정
			userRepository.save(user); // 사용자 정보 저장

			// 3분 후에 인증 코드를 삭제하는 타이머 설정
			new java.util.Timer().schedule(new java.util.TimerTask() {
				@Override
				public void run() {
					user.setCreCode(null); // 인증 코드 삭제
					userRepository.save(user); // 변경된 사용자 정보 저장
				}
			}, CODE_EXPIRATION_TIME * 60 * 1000);

			return new CommonResponseModel("00"); // 성공적으로 이메일을 발송했으면 "00" 반환
		} catch (MailException e) {
			return new CommonResponseModel("01"); // 이메일 발송 실패 시 "01" 반환
		}
	}

	// 인증 코드를 검증하는 메서드
	@Transactional // 트랜잭션 관리를 위해 어노테이션 추가
	public CommonResponseModel verifyCode(String membId, String code) {
		Optional<EmailAuthModel> optionalUser = userRepository.findByMembId(membId); // 회원 ID로 사용자 조회
		if (!optionalUser.isPresent()) {
			return new CommonResponseModel("01"); // 사용자가 존재하지 않으면 "01" 반환
		}

		EmailAuthModel user = optionalUser.get(); // 사용자 정보 가져오기
		String savedCode = user.getCreCode(); // 저장된 인증 코드 가져오기
		LocalDateTime savedCodeTime = user.getCreCodeTime(); // 저장된 인증 코드 생성 시간 가져오기
		LocalDateTime currentTime = LocalDateTime.now(); // 현재 시간 가져오기

		// 인증 코드가 없거나 유효 시간이 지난 경우 처리
		if (savedCode == null || savedCodeTime == null
				|| currentTime.isAfter(savedCodeTime.plusMinutes(CODE_EXPIRATION_TIME))) {
			user.setCreCode(null); // 인증 코드 삭제
			userRepository.save(user); // 변경된 사용자 정보 저장
			return new CommonResponseModel("02"); // 인증 코드 만료 시 "02" 반환
		}

		// 인증 코드가 일치하는 경우 처리
		if (savedCode.equals(code)) {
			user.setCreCode(null); // 인증 코드 삭제
			user.setCreCon("Y"); // 인증 완료 상태로 설정
			userRepository.save(user); // 변경된 사용자 정보 저장
			return new CommonResponseModel("00"); // 인증 성공 시 "00" 반환
		} else {
			return new CommonResponseModel("01"); // 인증 코드가 일치하지 않으면 "01" 반환
		}
	}

	// 랜덤한 인증 코드를 생성하는 메서드
	private String generateVerificationCode() {
		Random random = new Random();
		int code = random.nextInt(900000) + 100000; // 100000부터 999999까지의 랜덤 숫자 생성
		return String.valueOf(code); // 숫자를 문자열로 변환하여 반환
	}
}
