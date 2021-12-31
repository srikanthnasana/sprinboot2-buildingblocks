package com.stacksimply.restservices.projections;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public interface UserProjectionDTO {

	String getUserName();

	@Value("#{target.firstName+' '+target.lastName}")
	String getFullName();

	String getEmail();

	String getRole();

	@Value("#{target.userName+'-'+target.lastName}")
	String getUserNameandDesc();

	List<Order> getOrders();

	interface Order {
		Long getOredrId();

		String getOrderdescription();
	}

}
