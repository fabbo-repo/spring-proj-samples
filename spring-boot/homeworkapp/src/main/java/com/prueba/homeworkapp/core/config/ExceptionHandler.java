package com.prueba.homeworkapp.core.config;

import java.util.HashMap;
import java.util.Map;

import com.prueba.homeworkapp.core.domain.exceptions.ToDoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(value = {ToDoException.class})
	protected ResponseEntity<Object> handleConflict(ToDoException ex, WebRequest request) {
		Map<String, String> response = new HashMap<>();
		response.put("error", ex.getMessage());
		return handleExceptionInternal(
			ex, 
			response, 
			new HttpHeaders(), 
			ex.getHttpStatus(), 
			request
		);
	}
}
