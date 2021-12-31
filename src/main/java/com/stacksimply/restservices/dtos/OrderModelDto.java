package com.stacksimply.restservices.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_DEFAULT)
public class OrderModelDto {

	private Long oredrId;
	private String orderdescription;

	public OrderModelDto() {
		
	}
	public OrderModelDto(Long oredrId, String orderdescription) {
		super();
		this.oredrId = oredrId;
		this.orderdescription = orderdescription;
	}

	public Long getOredrId() {
		return oredrId;
	}

	public void setOredrId(Long oredrId) {
		this.oredrId = oredrId;
	}

	public String getOrderdescription() {
		return orderdescription;
	}

	public void setOrderdescription(String orderdescription) {
		this.orderdescription = orderdescription;
	}

}
