package com.stacksimply.restservices.exceptions;

import java.util.Date;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	// MethodArgumentsNotValidException
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomErrorDetails custErrDtls = new CustomErrorDetails(new Date(),
				"From MethodargumentsNotValid Exception GEH", ex.getMessage());
		return new ResponseEntity<>(custErrDtls, HttpStatus.BAD_REQUEST);
	}

	// HttpRequestMethodNotSupported
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomErrorDetails custErrDtls = new CustomErrorDetails(new Date(),
				"From MethodargumentsNotValid Exception GEH-Method Not Allowed", ex.getMessage());
		return new ResponseEntity<>(custErrDtls, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(UserNameNotFoundException.class)
	public final ResponseEntity<Object> handleUserNameNotFoundException(UserNameNotFoundException ex,
			WebRequest request) {
		CustomErrorDetails custErrDtls = new CustomErrorDetails(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(custErrDtls, HttpStatus.NOT_FOUND);
	}

	// ConstraintViolationException
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		CustomErrorDetails custErrDtls = new CustomErrorDetails(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(custErrDtls, HttpStatus.BAD_REQUEST);
	}

}
