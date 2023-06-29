package com.prueba.homeworkapp.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ToDoException extends RuntimeException {
	
	private final String message;
	
	private final HttpStatus httpStatus;

	public ToDoException(String message, HttpStatus httpStatus) {
		super(message);
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
