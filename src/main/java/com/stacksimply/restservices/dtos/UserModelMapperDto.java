package com.stacksimply.restservices.dtos;

import java.util.List;

public class UserModelMapperDto {

	private Long userId;
	
	private String userName;
	
	private String firstName;
	
	private List<OrderModelDto> orders;

	public List<OrderModelDto> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderModelDto> orders) {
		this.orders = orders;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
