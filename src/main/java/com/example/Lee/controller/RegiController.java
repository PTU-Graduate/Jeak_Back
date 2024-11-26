package com.example.Lee.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired; // 스프링의 의존성 주입 기능을 위한 어노테이션
import org.springframework.http.ResponseEntity; // HTTP 응답을 캡슐화하는 클래스
import org.springframework.web.bind.annotation.PostMapping; // POST 요청을 처리하는 메소드를 위한 어노테이션
import org.springframework.web.bind.annotation.RequestBody; // HTTP 요청 본문을 메소드 파라미터로 바인딩하는 어노테이션
import org.springframework.web.bind.annotation.RequestMapping; // 요청 URL을 매핑하기 위한 어노테이션
import org.springframework.web.bind.annotation.RestController; // REST 컨트롤러임을 나타내는 어노테이션
import org.springframework.web.context.request.RequestAttributes; // 요청범위와 세션범위를 지정
import org.springframework.web.context.request.RequestContextHolder; // Attribute 를 관리

import com.example.Lee.model.CommonResponseModel; // 공통 응답 모델 클래스
import com.example.Lee.model.RegiModel; // 회원 등록 정보 모델 클래스
import com.example.Lee.service.RegiService; // 회원 등록 서비스 클래스
import com.example.Lee.service.IdRegiService; // ID 등록 서비스 클래스
import com.example.Lee.service.StdRegiService; // 학번 등록 서비스 클래스
import com.example.Lee.service.MailRegiService; // Mail 등록 서비스 클래스
import com.example.Lee.model.BasicUserDataSave;


@RestController // 이 클래스가 REST 컨트롤러로 동작함을 스프링에게 알림
@RequestMapping("/PTU/Register") // "/PTU/Register" 경로로 들어오는 요청을 이컨트롤러로
public class RegiController {

	private final RegiService regiService; // 회원 등록 서비스 객체
	private final IdRegiService idRegiService; // ID 등록 서비스 객체
	private final StdRegiService stdRegiService; // 학번 등록 서비스 객체
	private final MailRegiService mailRegiService; // Mail 등록 서비스 객체

	@Autowired // 의존성 자동 주입. 스프링이 RegiService 타입의 객체를 자동으로 주입
	public RegiController(RegiService regiService, IdRegiService idRegiService, StdRegiService stdRegiService, MailRegiService mailRegiService) {
		this.regiService = regiService; 
		this.idRegiService = idRegiService;
        this.stdRegiService = stdRegiService;
        this.mailRegiService = mailRegiService;	// 생성자를 통해 주입받은 서비스 객체를 필드에 할당
	}

    @PostMapping("/ID") // ID 등록 엔드포인트
    public ResponseEntity<CommonResponseModel> registerId(@RequestBody Map<String, String> requestData) {
        RegiModel regiData = new RegiModel();
        regiData.setMembId(requestData.get("MEMB_ID"));

        // ID 중복 처리 후 결과 코드 확인
        CommonResponseModel idResult = idRegiService.registerId(regiData);
        if (!"00".equals(idResult.getRSLT_CD())) {
            return ResponseEntity.ok(idResult);
        }

   
        return ResponseEntity.ok(idResult);
    }

    @PostMapping("/StdNum") // 학번 등록 엔드포인트
    public ResponseEntity<CommonResponseModel> registerStdNum(@RequestBody Map<String, String> requestData) {
        RegiModel regiData = new RegiModel();
        regiData.setStdNum(requestData.get("STD_NUM"));

        // 학번 중복 처리 후 결과 코드 확인
        CommonResponseModel stdResult = stdRegiService.registerStd(regiData);
        if (!"00".equals(stdResult.getRSLT_CD())) {
            return ResponseEntity.ok(stdResult);
        }

        
    
        return ResponseEntity.ok(stdResult);
    }

    @PostMapping("/Mail") // 이메일 등록 엔드포인트
    public ResponseEntity<CommonResponseModel> registerMail(@RequestBody Map<String, String> requestData) {
        RegiModel regiData = new RegiModel();
        regiData.setEmail(requestData.get("EMAIL"));

        // 이메일 중복 처리 후 결과 코드 확인
        CommonResponseModel mailResult = mailRegiService.mailRegister(regiData);
        if (!"00".equals(mailResult.getRSLT_CD())) {
            return ResponseEntity.ok(mailResult);
        }
   
        return ResponseEntity.ok(mailResult);
    }
    
    @PostMapping("/basic-info-save")
    public ResponseEntity<BasicUserDataSave> registerBasicInfo(@RequestBody Map<String, String> requestData) {	
        RegiModel regiData = new RegiModel();
        regiData.setMembId(requestData.get("MEMB_ID"));
        regiData.setStdNum(requestData.get("STD_NUM"));
        regiData.setStdDepCd(requestData.get("STD_DEP_CD"));
        regiData.setName(requestData.get("NAME"));

        // 기본 정보와 SALT를 저장하고 결과 반환
        BasicUserDataSave result = regiService.basicRegiUserData(regiData);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/StdInfo") // 전체 정보 등록 엔드포인트
    public ResponseEntity<CommonResponseModel> registerStdInfo(@RequestBody Map<String, String> requestData) {
        // 세션에서 저장된 MEMB_ID, STD_NUM, EMAIL 가져오기
        RegiModel regiData = new RegiModel();
        regiData.setMembId(requestData.get("MEMB_ID"));
        regiData.setStdNum(requestData.get("STD_NUM"));
        regiData.setEmail(requestData.get("EMAIL"));
        regiData.setPass(requestData.get("PASS"));

        // 회원 정보 저장
        CommonResponseModel result = regiService.completeRegistration(regiData);

        // 리스트 반환
        return ResponseEntity.ok(result);
    }
}