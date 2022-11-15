package com.citiustech.appointment.exception;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.citiustech.appointment.dto.ResponseObject;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ResponseObject> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException exception, WebRequest request) {

		ResponseObject ResponseObject = new ResponseObject();
		ResponseObject.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		ResponseObject.setMessage(exception.getMessage());
		ResponseObject.setLocalDateTime(LocalDateTime.now());

		return new ResponseEntity<ResponseObject>(ResponseObject, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseObject> handleGeneralException(Exception exception, WebRequest request) {
		ResponseObject ResponseObject = new ResponseObject();
		ResponseObject.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		ResponseObject.setMessage(exception.getMessage());
		ResponseObject.setLocalDateTime(LocalDateTime.now());

		return new ResponseEntity<ResponseObject>(ResponseObject, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date());
		body.put("status code", HttpStatus.BAD_REQUEST.value());

		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("message", errors);

		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}


}