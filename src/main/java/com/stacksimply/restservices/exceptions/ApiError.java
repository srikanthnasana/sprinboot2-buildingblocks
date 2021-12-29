package com.stacksimply.restservices.exceptions;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class ApiError {

	private int status;
	private String message;
	private Long timestamp;
	private String path;
	Map<String, String> validationErrors;
	
	
    public ApiError() {
    	
    }
	public ApiError(int status, String message,String path) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = new Date().getTime();
		this.path = path;
		
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(Map<String, String> validationErrors) {
		this.validationErrors = validationErrors;
	}

}
