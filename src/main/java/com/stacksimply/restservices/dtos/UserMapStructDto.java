package com.stacksimply.restservices.dtos;

import java.util.List;

public class UserMapStructDto {

	private Long id;
	private String userName;
	private String emailaddress;
	private String userRole;
	private List<OrderModelDto> orders;

	


	public UserMapStructDto() {
     super();
	}

	

	public UserMapStructDto(Long id, String userName, String emailaddress, String userRole,
			List<OrderModelDto> orders) {
		super();
		this.id = id;
		this.userName = userName;
		this.emailaddress = emailaddress;
		this.userRole = userRole;
		this.orders = orders;
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
	
	public List<OrderModelDto> getOrders() {
		return orders;
	}



	public void setOrders(List<OrderModelDto> orders) {
		this.orders = orders;
	}


}
