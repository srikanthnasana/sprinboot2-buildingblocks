package com.stacksimply.restservices.exceptions;

import java.util.Date;

//custom Error Dtls Bean
public class CustomErrorDetails {
	private Date timeStamp;
	private String message;
	private String errorDtls;

	public CustomErrorDetails() {

	}

	public CustomErrorDetails(Date timeStamp, String message, String errorDtls) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.errorDtls = errorDtls;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorDtls() {
		return errorDtls;
	}

	public void setErrorDtls(String errorDtls) {
		this.errorDtls = errorDtls;
	}

}
