package com.stacksimply.restservices.dtos;

public class UserMapStructDto {

	private Long id;
	private String userName;
	private String emailaddress;
	private String userRole;

	public UserMapStructDto() {
     super();
	}

	public UserMapStructDto(Long userId, String userName, String emailaddress,String userRole) {
		super();
		this.id = userId;
		this.userName = userName;
		this.emailaddress = emailaddress;
		this.userRole=userRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long userId) {
		this.id = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	

}
