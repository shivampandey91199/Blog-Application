package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobaleExceptionHandler {

	@ExceptionHandler(ResourceNotFoundExceptions.class)
	public ResponseEntity<ApiResponse> resourceNotFounfExceptionHandler(ResourceNotFoundExceptions ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException mex) {
		Map<String, String> resp = new HashMap<>();

		mex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldname = ((FieldError) error).getField();
			String defaultMessage = error.getDefaultMessage();
			resp.put(fieldname, defaultMessage);
		});
		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<ApiResponse> handlerAPiException(APIException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}
