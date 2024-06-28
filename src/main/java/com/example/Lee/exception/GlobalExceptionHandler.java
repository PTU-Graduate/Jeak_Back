package com.example.Lee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.Lee.model.CommonResponseModel;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CommonResponseModel> handleAllExceptions(Exception ex) {
		// 여기에서 로그를 남기거나 추가적인 처리를 할 수 있음
		CommonResponseModel response = new CommonResponseModel("99");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<CommonResponseModel> handleIllegalArgumentException(IllegalArgumentException ex) {
		CommonResponseModel response = new CommonResponseModel("99");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
