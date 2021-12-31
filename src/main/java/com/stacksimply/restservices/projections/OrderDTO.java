package com.stacksimply.restservices.projections;

public class OrderDTO {

	private Long oredrId;
	private String orderdescription;

	/*
	 * public OrderDTO(Long oredrId, String orderdescription) { super();
	 * this.oredrId = oredrId; this.orderdescription = orderdescription; }
	 */

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
